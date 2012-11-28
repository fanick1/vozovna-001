package cz.muni.fi.pa165.vozovna.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import cz.muni.fi.pa165.vozovna.service.ServiceIntervalService;
import cz.muni.fi.pa165.vozovna.validators.ServiceIntervalValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("intervalDTO")
public class IntervalsController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    ServiceIntervalService serviceIntervalService;

    @Autowired
    private VehicleService vehicleService;

     @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new ServiceIntervalValidator());
 
    }
    
    private void initData() {
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setBrand("dsgds");
        vehicle.setDistanceCount(23);
        vehicle.setEngineType("235");
        vehicle.setType("ewgre");
        vehicle.setUserClass(UserClassEnum.EMPLOYEE);
        vehicle.setVin("dfhgfdh");
        vehicle.setYearMade(2011);

        this.vehicleService.create(vehicle);

        ServiceIntervalDTO interval = new ServiceIntervalDTO();
        interval.setDescription("hre");
        interval.setInspectionInterval(4);
        interval.setVehicle(vehicle);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            List<Date> dates = new ArrayList<>();
            dates.add(dateFormat.parse("11.3.2012"));
            dates.add(dateFormat.parse("14.4.2012"));
            interval.setDated(dates);
        } catch (Exception ex) {
            System.out.println("Error while initializing data: " + ex.getMessage());
        }

        this.serviceIntervalService.create(interval);
    }

    @RequestMapping(value = "/admin/intervals/index", method = RequestMethod.GET)
    public String printWelcome(HttpServletRequest request, ModelMap model) {

        if(this.serviceIntervalService.findAll().size() == 0) {
            this.initData();
        }

        model.put("intervals", this.serviceIntervalService.findAll());

        return "/admin/intervals/index";
    }

    //Add
    @RequestMapping(value = "/admin/intervals/add", method = RequestMethod.GET)
    public String viewEmptyForm(HttpServletRequest request, ModelMap model) {

        System.out.println("In add method");
        String vehicleID = request.getParameter("vehicleId");
        System.out.println("Vehicle ID: " + vehicleID);
        if(vehicleID == null) {
            this.logger.fatal("Cannot find vehicle");
            return "admin/intervals/index";
        }

        VehicleDTO vehicle = this.vehicleService.getById(Long.parseLong(vehicleID));
        ServiceIntervalDTO interval = new ServiceIntervalDTO();
        interval.setVehicle(vehicle);

        model.put("intervalDTO", interval);
        return "admin/intervals/addOrEdit";
    }


    // Edit
    @RequestMapping(value = "/admin/intervals/edit", params = "id", method = RequestMethod.GET)
    public String viewFilledForm(@RequestParam("id") Long id, @RequestParam(value = "vehicleId", required = false) Long vehicleID, HttpServletRequest request,  ModelMap model, HttpSession session) throws Exception {

        ServiceIntervalDTO interval = this.serviceIntervalService.getById(id);
        if(vehicleID != null) {
            VehicleDTO vehicleDTO = this.vehicleService.getById(vehicleID);
            interval.setVehicle(vehicleDTO);
        }

        if (interval == null) {
            session.setAttribute("error", "admin.intervals.edit.msg.unexists");
            return "redirect:/admin/intervals/index";
        }
        model.put("intervalDTO", interval);
        return "admin/intervals/addOrEdit";
    }

    @RequestMapping(value = { "/admin/intervals/add", "/admin/intervals/edit" }, method = RequestMethod.POST)
    public String submitEditForm(@Validated @ModelAttribute("intervalDTO") ServiceIntervalDTO interval,  BindingResult result,
            ModelMap model, HttpSession session) {
        
        if (result.hasErrors()) {
            model.put("intervalDTO", interval);
            return "admin/intervals/addOrEdit";
        }
        
        if (interval.getId() == null) {
            this.serviceIntervalService.create(interval);
            session.setAttribute("message", "admin.intervals.create.msg.successful");
        } else {
            this.serviceIntervalService.update(interval);
            session.setAttribute("message", "admin.intervals.update.msg.successful");
        }

        return "redirect:/admin/intervals/show?id=" + interval.getId();
    }

    // Vehicle select
    @RequestMapping(value = "/admin/intervals/vehicleSelect",method = RequestMethod.GET)
    public String showVehicles(ModelMap model) throws Exception {

        model.put("vehicles", this.vehicleService.findAll());
        return "/admin/intervals/vehicleSelect";
    }

    // Vehicle select
    @RequestMapping(value = "/admin/intervals/vehicleSelect2",method = RequestMethod.GET)
    public String showVehiclesForEdit(HttpServletRequest request, ModelMap model) throws Exception {

        model.put("vehicles", this.vehicleService.findAll());
        return "/admin/intervals/vehicleSelect2";
    }

    // Show
    @RequestMapping(value = "/admin/intervals/show", params = "id")
    public String viewVehicle(@RequestParam("id") Long id, ModelMap model, HttpSession session) throws Exception {
        ServiceIntervalDTO interval = this.serviceIntervalService.getById(id);
        if (interval == null) {
            session.setAttribute("error", "admin.intervals.show.msg.unexists");
            return "redirect:/admin/intervals/index";
        }
        model.put("interval", interval);
        return "admin/intervals/show";
    }

    // Delete
    @RequestMapping(value = "/admin/intervals/delete", params = "id")
    public String deleteById(@RequestParam("id") Long id, HttpSession session) throws Exception {

        ServiceIntervalDTO interval = this.serviceIntervalService.getById(id);

        if (interval == null) {
            session.setAttribute("error", "admin.intervals.delete.msg.unexists");
            return "redirect:/admin/intervals/index";
        }

        this.serviceIntervalService.remove(interval);
        session.setAttribute("message", "admin.intervals.delete.msg.successful");
        return "redirect:/admin/intervals/index";
    }
}
