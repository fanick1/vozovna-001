package cz.muni.fi.pa165.vozovna.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.DriveService;
import cz.muni.fi.pa165.vozovna.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import cz.muni.fi.pa165.vozovna.service.VehicleService;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes("driveDTO")
public class UserVehiclesController {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    protected final Log logger = LogFactory.getLog(getClass());
    private Date startDate;
    private Date endDate;
    private Long vehicleId;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DriveService driveService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
	        @Override
	        public String getAsText() {
		        Date date = (Date) getValue();
		        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		        return dateFormat.format(date);
	        }

	        @Override
	        public void setAsText(String s) throws IllegalArgumentException {
		        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		        try {
			        setValue(dateFormat.parse(s));
		        } catch (ParseException e) {
			        throw new RuntimeException("Failed to parse string '" + s + "' to date");
		        }

	        }

        });
    }

//    private void disposeData() {
//        this.vehicleId = null;
//    }

    @RequestMapping(value = "/vehicles", method = RequestMethod.GET)
    public String userVehicles(HttpServletRequest request, ModelMap model, Principal principal) {

//        this.disposeData();

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        System.out.println("Start date: " + startDate + ", End date: " + endDate);

        if(startDate == null || endDate == null)
        {
            model.addAttribute("vehicles",new ArrayList<VehicleDTO>());
        }
        else
        {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	        User user = this.userService.getByUsername(principal.getName()).toNewUser();
	        try {
	            model.addAttribute("vehicles",
			            this.vehicleService.getAvailableVehicles(
					            user, new DateTime(dateFormat.parse(startDate)), new DateTime(dateFormat.parse(endDate))));
	        } catch (ParseException ex) {
		        logger.fatal(ex.getMessage());
	        }
        }
        return "vehicles";
    }

    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    public String filterVehicles(@RequestParam(value = "startDate") Date startDate,
                                 @RequestParam(value = "endDate") Date endDate) {

        this.startDate = startDate;
        this.endDate = endDate;

        System.out.println(" IN POST METHOD ");
        System.out.println(startDate + " " + endDate);
        if (logger.isDebugEnabled()) {
            System.out.println("DEBUG IS ENABLED");
        } else {
            System.out.println("DEBUG IS NOT ENABLED");
        }
        logger.debug(startDate + " " + endDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return "redirect:/vehicles?startDate=" + dateFormat.format(startDate) + "&endDate=" + dateFormat.format(endDate);
    }

    @RequestMapping(value = "/reserveForm", method = RequestMethod.GET)
    public String reserveVehicle(@RequestParam("id") Long vehicleId,ModelMap model, Principal principal) {

        UserDTO user = this.userService.getByUsername(principal.getName());

        VehicleDTO vehicle = this.vehicleService.getById(vehicleId);

        DriveDTO drive = new DriveDTO();
        drive.setUser(user);
        drive.setVehicle(vehicle);
        drive.setDateFrom(new DateTime(this.startDate));
        drive.setDateTo(new DateTime(this.endDate));
        drive.setState(DriveStateEnum.RESERVED);

        this.vehicleId = vehicle.getId();

        model.put("driveDTO", drive);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy");
       logger.info("Reserving form for vehicle " + vehicle.toString() + " on dates " + dateFormat.format(this.startDate) + " - " + dateFormat.format(this.endDate)
       + " by user " + user.toString());

        return "reserveForm";
    }

    @RequestMapping(value = "/reserveForm", method = RequestMethod.POST)
    public String reserveVehicleOperation(@ModelAttribute("driveDTO") DriveDTO drive) {

        if(drive != null) {
            logger.info("Drive " + drive + " created!");
            System.out.println("Drive " + drive + " created!");
            this.driveService.create(drive);
        }
        else {
            logger.info("Drive not found!");
            System.out.println("Drive not found!");
        }

        return "redirect:/drives";
    }
}
