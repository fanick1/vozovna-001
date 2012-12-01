package cz.muni.fi.pa165.vozovna.controller;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.editors.DateTimeEditor;
import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.DriveService;
import cz.muni.fi.pa165.vozovna.service.UserService;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserVehiclesController {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DriveService driveService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(DateTime.class, new DateTimeEditor());
    }

    @RequestMapping(value = "/vehicles", method = RequestMethod.GET, params = {"!from", "!to"})
    public String userVehicles(ModelMap model, HttpSession session) {
        model.addAttribute("vehicles", new ArrayList<VehicleDTO>());
        return "vehicles";
    }

    @RequestMapping(value = "/vehicles", method = RequestMethod.GET, params = {"from", "to"})
    public String userVehiclesFiltred(@RequestParam("from") String from,
            @RequestParam("to") String to,
            Principal principal,
            ModelMap model, HttpSession session) {

        if (from == null && to == null) {
            model.addAttribute("vehicles", new ArrayList<VehicleDTO>());
        } else if ((from == null && to != null) || (from != null && to == null)) {
            session.setAttribute("error", "user.vehicles.err.bothDates");
            model.addAttribute("vehicles", new ArrayList<VehicleDTO>());
            model.addAttribute("dateFrom", from);
            model.addAttribute("dateTo", to);
        } else {
            DateTime dateFrom = new DateTime(from);
            DateTime dateTo = new DateTime(to);
            model.addAttribute("dateFrom", from);
            model.addAttribute("dateTo", to);

            if (dateFrom.isAfter(dateTo)) {
                session.setAttribute("error", "user.vehicles.err.changedDates");
                return "vehicles";
            }


            UserDTO user = this.userService.getByUsername(principal.getName());
            logger.info("filtering vehicles: \nfrom=" + from + " \nto=" + to + " \nuser=" + user);
            List<VehicleDTO> vehicles = vehicleService.getAvailableVehicles(user.getUserClass(), dateFrom, dateTo);
            model.addAttribute("vehicles", vehicles);

        }
        return "vehicles";
    }

    @RequestMapping(value = "/reserveForm", method = RequestMethod.GET)
    public String reserveVehicle(@RequestParam("id") Long vehicleId, @RequestParam("from") DateTime from, @RequestParam("to") DateTime to, Principal principal, ModelMap model, HttpSession session) {

        if (from == null || to == null || vehicleId == null) {
            session.setAttribute("error", "user.vehicles.err.wrongParams");
            return "redirect:/vehicles";
        }

        UserDTO user = userService.getByUsername(principal.getName());
        VehicleDTO vehicle = vehicleService.getById(vehicleId);

        if (vehicle == null || user == null
                || !vehicle.getUserClass().equals(user.getUserClass())) {
            session.setAttribute("error", "user.vehicles.err.wrongParams");
            return "redirect:/vehicles";
        }
        
        List<VehicleDTO> availableVehicles = vehicleService.getAvailableVehicles(user.getUserClass(), from, to);
        if (!availableVehicles.contains(vehicle)) {
            session.setAttribute("error", "user.vehicles.err.wrongParams");
            return "redirect:/vehicles";
        }

        DriveDTO drive = new DriveDTO();
        //drive.setUser(user);
        drive.setUserId(user.getId());
        //drive.setVehicle(vehicle);
        drive.setVehicleId(vehicle.getId());
        drive.setDateFrom(from);
        drive.setDateTo(to);
        drive.setState(DriveStateEnum.RESERVED);

        driveService.create(drive);
        logger.info("Drive " + drive + " created!");

        return "redirect:/drives";
    }

    @RequestMapping(value = "/reserveForm", method = RequestMethod.POST)
    public String reserveVehicleOperation(@ModelAttribute("driveDTO") DriveDTO drive) {

        if (drive != null) {
            logger.info("Drive " + drive + " created!");
            this.driveService.create(drive);
        } else {
            logger.info("Drive not found!");
            System.out.println("Drive not found!");
        }

        return "redirect:/drives";
    }
}
