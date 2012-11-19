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
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
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
    public String viewVehiclesList(Model model) throws Exception {
        //Map<String, Object> model = new HashMap<String, Object>();
        List<VehicleDTO> vehicleDTOs = null;

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
    public String viewVehiclesList(@RequestParam("id") Long id, Model model) throws Exception {

        VehicleDTO dto = vehicleService.getById(id);

        model.addAttribute("vehicle", dto.toVehicle());
        
        return "admin/vehicles/show";
    }
    
    // Add
    @RequestMapping(value = "/admin/vehicles/add", method = RequestMethod.GET)
    public String viewEmptyForm(Model model) 
            throws PageNotFoundException 
    {
        Vehicle newVehicle = new Vehicle();

        model.addAttribute("vehicle", newVehicle);
        return "admin/vehicles/addOrEdit";
    }

    // Edit
    @RequestMapping(value = "/admin/vehicles/edit", params = "id", method = RequestMethod.GET)
    public String viewFilledForm(@RequestParam("id") Long id, Model model) throws Exception {
        VehicleDTO vehicleDTOToEdit = vehicleService.getById(id);
        if (vehicleDTOToEdit == null) {
            throw new PageNotFoundException("Vehicle with id " + id + "doesn't exist.");
        }
        
        model.addAttribute("vehicle", (vehicleDTOToEdit).toVehicle());
        
        return "admin/vehicles/addOrEdit";
    }

    // Submited add/edit form
    @RequestMapping(value = {"/admin/vehicles/add", "/admin/vehicles/edit"}, method = RequestMethod.POST)
    public String submitEditForm(@Validated @ModelAttribute("vehicle") Vehicle vehicle, BindingResult result, Model model)
            throws PageNotFoundException 
    {

        if (result.hasErrors()) {
            return "/admin/vehicles/addOrEdit";
        }

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
        if (vehicle.getId() == null) {
            vehicleService.create(vehicleDTO);
        } else {
            vehicleService.update(vehicleDTO);
        }

        return "redirect:/admin/vehicles/show?id=" + vehicleDTO.getId();
    }

    // Delete
    @RequestMapping(value = "/admin/vehicles/delete", params = "id")
    public String deleteById(@RequestParam("id") Long id) throws Exception {

        VehicleDTO vehicleToDelete = vehicleService.getById(id);

        if (vehicleToDelete == null) {
            throw new PageNotFoundException("Vehicle with id " + id + "doesn't exist.");
        }

        vehicleService.remove(vehicleToDelete);

        return "redirect:/admin/vehicles/";
    }
    
}
