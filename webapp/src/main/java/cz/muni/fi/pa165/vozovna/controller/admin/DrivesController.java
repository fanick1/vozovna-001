package cz.muni.fi.pa165.vozovna.controller.admin;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.editors.DateTimeEditor;
import cz.muni.fi.pa165.vozovna.editors.UserEditor;
import cz.muni.fi.pa165.vozovna.editors.VehicleDTOEditor;
import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import cz.muni.fi.pa165.vozovna.service.DriveService;
import cz.muni.fi.pa165.vozovna.service.UserService;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import cz.muni.fi.pa165.vozovna.validators.DriveFormValidator;

/**
 * @author Eva Neduchalová, 359893
 */
@Controller
public class DrivesController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    DriveService driveService;

    @Autowired
    UserService userService;

    @Autowired
    VehicleService vehicleService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new DriveFormValidator());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(UserDTO.class, new UserEditor(userService));
        binder.registerCustomEditor(VehicleDTO.class, new VehicleDTOEditor(vehicleService));
        binder.registerCustomEditor(DateTime.class, new DateTimeEditor());
    }

    @RequestMapping(value = "/admin/drives", method = RequestMethod.GET)
    public String viewAll(ModelMap model, HttpSession session) {
        model.put("drives", driveService.findAll());
        return "/admin/drivesList";
    }

    @RequestMapping(value = "/admin/drives/new", method = RequestMethod.GET)
    public String showNewForm(ModelMap model, HttpSession session) {
        model.put("driveDTO", new DriveDTO());
        model.put("users", userService.findAll());
        model.put("vehicles", vehicleService.findAll());
        model.put("states", DriveStateEnum.values());
        return "/admin/driveForm";
    }

    @RequestMapping(value = "/admin/drives/new", method = RequestMethod.POST)
    public String createNew(@Validated DriveDTO driveForm, BindingResult result, ModelMap model, HttpSession session) {
        if (result.hasErrors()) {
            model.put("users", userService.findAll());
            model.put("vehicles", vehicleService.findAll());
            model.put("states", DriveStateEnum.values());
            return "/admin/driveForm";
        }
        driveService.create(driveForm);
        session.setAttribute("message", "drive.msg.create.successful");
        return "redirect:/admin/drives/";
    }

    @RequestMapping(value = "/admin/drives/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
        DriveDTO driveToEdit = driveService.getById(id);
        if (driveToEdit == null) {
            session.setAttribute("error", "drive.msg.edit.unexists");
            return "redirect:/admin/drives/";
        }
        model.put("users", userService.findAll());
        model.put("vehicles", vehicleService.findAll());
        model.put("states", DriveStateEnum.values());
        model.put("driveDTO", driveToEdit);
        return "/admin/driveForm";
    }

    @RequestMapping(value = "/admin/drives/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") Long id, @Validated @ModelAttribute("drive") DriveDTO driveForm, BindingResult result,
            ModelMap model, HttpSession session) {
        if (result.hasErrors()) {
            model.put("users", userService.findAll());
            model.put("vehicles", vehicleService.findAll());
            model.put("states", DriveStateEnum.values());
            return "/admin/driveForm";
        }
        if (id == null || driveForm.getId() == null || !id.equals(driveForm.getId())) {
            session.setAttribute("error", "drive.msg.edit.wrongId");
            model.put("users", userService.findAll());
            model.put("vehicles", vehicleService.findAll());
            model.put("states", DriveStateEnum.values());
            return "/admin/driveForm";
        }
        driveService.update(driveForm);
        session.setAttribute("message", "drive.msg.edit.successful");
        return "redirect:/admin/drives/";
    }

    @RequestMapping(value = "/admin/drives/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
        DriveDTO driveToDelete = driveService.getById(id);
        if (driveToDelete == null) {
            session.setAttribute("error", "drive.msg.delete.unexists");
        } else {
            driveService.remove(driveToDelete);
            session.setAttribute("message", "drive.msg.delete.successful");
        }
        return "redirect:/admin/drives";
    }

}
