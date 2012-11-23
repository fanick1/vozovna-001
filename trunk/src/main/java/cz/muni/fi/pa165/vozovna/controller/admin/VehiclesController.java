package cz.muni.fi.pa165.vozovna.controller.admin;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.editors.VehicleEditor;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.exceptions.PageNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.muni.fi.pa165.vozovna.service.VehicleService;
import cz.muni.fi.pa165.vozovna.validators.VehicleValidator;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VehiclesController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    VehicleService vehicleService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new VehicleValidator());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Vehicle.class, new VehicleEditor(vehicleService));
    }
    
    // List
    @RequestMapping(value = "/admin/vehicles")
    public String viewVehiclesList(ModelMap model, HttpSession session) throws Exception {
        List<VehicleDTO> vehicleDTOs;

        vehicleDTOs = vehicleService.findAll();

        List<Vehicle> vehicles = new LinkedList<>();
        for (VehicleDTO dto: vehicleDTOs) {
            vehicles.add(dto.toVehicle());
        }
        
        model.addAttribute("vehicles", vehicles);
        
        return "admin/vehicles/index";
    }
    
     // Show
    @RequestMapping(value = "/admin/vehicles/show", params="id")
    public String viewVehiclesList(@RequestParam("id") Long id, ModelMap model, HttpSession session) throws Exception {

        VehicleDTO vehicle = vehicleService.getById(id);

        model.put("vehicle", vehicle);
        
        return "admin/vehicles/show";
    }
    
    // Add
    @RequestMapping(value = "/admin/vehicles/add", method = RequestMethod.GET)
    public String viewEmptyForm(ModelMap model, HttpSession session) 
            throws PageNotFoundException 
    {
        VehicleDTO newVehicle = new VehicleDTO();

        model.put("vehicleDTO", newVehicle);
        return "admin/vehicles/addOrEdit";
    }

    // Edit
    @RequestMapping(value = "/admin/vehicles/edit", params = "id", method = RequestMethod.GET)
    public String viewFilledForm(@RequestParam("id") Long id, ModelMap model, HttpSession session) throws Exception {
        VehicleDTO vehicleDTOToEdit = vehicleService.getById(id);
        if (vehicleDTOToEdit == null) {
            throw new PageNotFoundException("Vehicle with id " + id + "doesn't exist.");
        }
        
        model.put("vehicleDTO", vehicleDTOToEdit);
        
        return "admin/vehicles/addOrEdit";
    }

    // Submited add/edit form
    @RequestMapping(value = {"/admin/vehicles/add", "/admin/vehicles/edit"}, method = RequestMethod.POST)
    public String submitEditForm(@Validated @ModelAttribute("vehicle") VehicleDTO vehicle, BindingResult result, ModelMap model, HttpSession session)
            throws PageNotFoundException 
    {

        if (result.hasErrors()) {
            model.put("vehicleDTO", vehicle);
            return "/admin/vehicles/addOrEdit";
        }

        //VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
        if (vehicle.getId() == null) {
            vehicleService.create(vehicle);
            session.setAttribute("message", "admin.vehicles.create.msg.successful");
        } else {
            vehicleService.update(vehicle);
            session.setAttribute("message", "admin.vehicles.update.msg.successful");
        }

        return "redirect:/admin/vehicles/show?id=" + vehicle.getId();
    }

    // Delete
    @RequestMapping(value = "/admin/vehicles/delete", params = "id")
    public String deleteById(@RequestParam("id") Long id, ModelMap model, HttpSession session) throws Exception {

        VehicleDTO vehicleToDelete = vehicleService.getById(id);

        if (vehicleToDelete == null) {
            throw new PageNotFoundException("Vehicle with id " + id + "doesn't exist.");
        }

        vehicleService.remove(vehicleToDelete);
        session.setAttribute("message", "admin.vehicles.delete.msg.successful");
        return "redirect:/admin/vehicles/";
    }
    
}
