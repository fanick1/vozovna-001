package cz.muni.fi.pa165.vozovna.controller;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import cz.muni.fi.pa165.vozovna.exceptions.PageNotFoundException;
import java.security.Principal;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.muni.fi.pa165.vozovna.service.DriveService;
import cz.muni.fi.pa165.vozovna.service.UserService;
import cz.muni.fi.pa165.vozovna.validators.DriveFinishedValidator;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserDrivesController {
    
//    protected final Log logger = LogFactory.getLog(getClass());
//    
//    @Autowired
//    DriveService driveService;
//
//    @RequestMapping(value = "/drives", method = RequestMethod.GET)
//    public String userDrives(HttpServletRequest request, ModelMap model, Principal principal) {
//        
//        //model.put("drives", driveService.findByUser(user));
//        logger.debug("tajcto");
//
//        return "drives";
//    }

    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    DriveService driveService;

    @Autowired
    UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new DriveFinishedValidator());
    }
    
    /**
     * 
     * @param model
     * @param principal
     * @return
     * @throws PageNotFoundException
     */
    @RequestMapping(value = "/drives", method = RequestMethod.GET)
    public String viewVehiclesList(ModelMap model, Principal principal) throws PageNotFoundException {
        //get current User
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (principalName == null) {
            throw new PageNotFoundException("Couldn't get current user's username.");
        }
        UserDTO user = userService.getByUsername(principalName); 
        if (user == null) {
            throw new PageNotFoundException("Current user doesn't exist.");
        }
        
        // find drives
        List<Criterion> crits = new LinkedList<>();
        crits.add(Restrictions.eq("user.id", user.getId()));
        List<Order> orders = new LinkedList<>();
        orders.add(Order.desc("dateFrom"));
        List<DriveDTO> drives = driveService.findByCriteria(crits, orders);
       
        model.put("drives", drives);
       
        return "/drives/index";
    }
    
   
    // Cancel
    @RequestMapping(value = "/drives/cancel", params={"id"}) 
    public String actionCancelDrive(@RequestParam("id") Long id, ModelMap model, HttpSession session) throws Exception {
        if (id == null) {
            throw new PageNotFoundException("Missing argument id.");
        }
        // get drive
        DriveDTO drive = driveService.getById(id);
        if (drive == null) {
            throw new PageNotFoundException("Drive not found.");
        }
        
        // check user's permission
        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new PageNotFoundException("Current user didn't found.");
        }
        if (drive.getUser().getId() != currentUser.getId()) {
            throw new PageNotFoundException("User can't change state of foreign drive.");
        }
        
        // check state of current drive 
        if (!drive.getState().equals(DriveStateEnum.RESERVED)) {
            // only drive with state RESERVED can be cancelled
            session.setAttribute("error", "drives.msg.cancel.invalidAction");
            return "redirect:/drives"; 
        }

        // change
        drive.setState(DriveStateEnum.CANCELLED);
        driveService.update(drive);
        
        session.setAttribute("message", "drives.msg.cancel.successful");
       
        return "redirect:/drives"; 
    }
    
    // Start drive
    @RequestMapping(value = "/drives/start", params="id") 
    public String actionChangeDriveState(@RequestParam("id") Long id, ModelMap model, HttpSession session) throws Exception {
        if (id == null) {
            throw new PageNotFoundException("Missing argument id.");
        }

        // get drive
        DriveDTO drive = driveService.getById(id);
        if (drive == null) {
            throw new PageNotFoundException("Drive not found.");
        }
        
        // check user's permission
        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new PageNotFoundException("Current user didn't found.");
        }
        if (drive.getUser().getId() != currentUser.getId()) {
            throw new PageNotFoundException("User can't change state of foreign drive.");
        }
   
        // check state of current drive 
        if (!drive.getState().equals(DriveStateEnum.RESERVED)) {
            // only drive with state RESERVED can be cancelled
            session.setAttribute("error", "drives.msg.changeState.invalidAction.start");
            return "redirect:/drives"; 
        }
        // is time to pick vehicle?
        DateTime now = new DateTime();
        if (now.compareTo(drive.getDateFrom()) < 0) {
            // it's to soon to pick a vehicle
            session.setAttribute("error", "drives.msg.start.tooSoon");
            return "redirect:/drives"; 
        }
        if (now.compareTo(drive.getDateTo()) > 0) {
            // it's to soon to pick a vehicle
            session.setAttribute("error", "drives.msg.start.tooLate");
            return "redirect:/drives"; 
        }
        // is vehicle available
        List<Criterion> crits = new LinkedList<>();
        crits.add(Restrictions.eq("vehicle.id", drive.getVehicle().getId()));
        crits.add(Restrictions.eq("state", DriveStateEnum.ONGOING));
        List<DriveDTO> unfinishedDrives = driveService.findByCriteria(crits, null);
        if (unfinishedDrives.size() > 0) {
            // vehicle is still not returned
            session.setAttribute("error", "drives.msg.start.notReturnedVehicle");
            return "redirect:/drives"; 
        }

        // change
        drive.setState(DriveStateEnum.ONGOING);
        driveService.update(drive);
        session.setAttribute("message", "drives.msg.start.successful");
  
                   
        return "redirect:/drives"; 
    }
    
    // Finish drive Form
    @RequestMapping(value = "/drives/finish", params = "id", method = RequestMethod.GET)
    public String viewFinishForm(@RequestParam("id") Long id, ModelMap model, HttpSession session) throws Exception {
        
        if (id == null) {
            throw new PageNotFoundException("Missing argument ID.");
        }
        // get drive
        DriveDTO driveDTOToFinish = driveService.getById(id);
        if (driveDTOToFinish == null) {
            throw new PageNotFoundException("Drive with id " + id + "doesn't exist.");
        }
        
        // check user's permission
        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new PageNotFoundException("Current user didn't found.");
        }
        if (driveDTOToFinish.getUser().getId() != currentUser.getId()) {
            throw new PageNotFoundException("User can't change state of foreign drive.");
        }
        
        // check state of current drive 
        if (!driveDTOToFinish.getState().equals(DriveStateEnum.ONGOING)) {
            // only drive with state RESERVED can be cancelled
            session.setAttribute("error", "drives.msg.finish.invalidAction");
            return "redirect:/drives";
        }
        
        model.put("drive", driveDTOToFinish);
        
        return "drives/finish";
    }


    @RequestMapping(value = "/drives/finish", method = RequestMethod.POST)
    public String submittedFinishForm(@ModelAttribute("drive") DriveDTO drive, BindingResult result, ModelMap model, HttpSession session) throws Exception {
        if (drive == null || drive.getId() == null) {
            throw new PageNotFoundException("Missing drive.");
        }
        if (result.hasErrors()) {
            return "drives/finish";
        }
        DriveDTO driveDTO = driveService.getById(drive.getId());
        if (driveDTO == null) {
            throw new PageNotFoundException("Drive didn't found.");
        }
        // check user's permission
        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new PageNotFoundException("Current user didn't found.");
        }
        if (driveDTO.getUser().getId() != currentUser.getId()) {
            throw new PageNotFoundException("User can't change state of foreign drive.");
        }
        
        // check state of current drive 
        if (!driveDTO.getState().equals(DriveStateEnum.ONGOING)) {
            // only drive with state RESERVED can be cancelled
            session.setAttribute("error", "drives.msg.finish.invalidAction");
            return "redirect:/drives";
        }
        // change drive
        driveDTO.setDistance(drive.getDistance());
        driveDTO.setState(DriveStateEnum.FINISHED);
            
        driveService.update(driveDTO);
        session.setAttribute("message", "drives.msg.finish.successful");
        
        return "redirect:/drives";
    }
    
    /**
     * Returns current user
     * 
     * @return Current user or null if not found
     */
    private UserDTO getCurrentUser() {
        // check user permission to do that
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (principalName == null) {
            return null;
        }
        UserDTO currentUser = userService.getByUsername(principalName); 
        if (currentUser == null) {
            return null;
        }
        return currentUser;
    }
}
