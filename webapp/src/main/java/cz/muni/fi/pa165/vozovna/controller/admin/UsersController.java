package cz.muni.fi.pa165.vozovna.controller.admin;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.UserService;
import cz.muni.fi.pa165.vozovna.validators.UserFormValidator;

/**
 * 
 * @author Eva Neduchalová
 * 
 */
@Controller
public class UsersController {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserFormValidator());
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String viewAll(ModelMap model, HttpSession session) {
        model.put("users", userService.findAll());
        return "/admin/usersList";
    }

    @RequestMapping(value = "/admin/users/new", method = RequestMethod.GET)
    public String showNewForm(ModelMap model, HttpSession session) {
        model.put("userDTO", new UserDTO());
        model.put("userClasses", UserClassEnum.values());
        return "/admin/userForm";
    }

    @RequestMapping(value = "/admin/users/new", method = RequestMethod.POST)
    public String createNew(@Validated UserDTO userForm, BindingResult result, ModelMap model, HttpSession session) {
        if (result.hasErrors()) {
            model.put("userClasses", UserClassEnum.values());
            return "/admin/userForm";
        }
        userService.create(userForm);
        session.setAttribute("message", "user.msg.create.successful");
        return "redirect:/admin/users/";
    }

    @RequestMapping(value = "/admin/users/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
        UserDTO userToEdit = userService.getById(id);
        if (userToEdit == null) {
            session.setAttribute("error", "user.msg.edit.unexists");
            return "redirect:/admin/users/";
        }
        model.put("userDTO", userToEdit);
        model.put("userClasses", UserClassEnum.values());
        return "/admin/userForm";
    }

    @RequestMapping(value = "/admin/users/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") Long id, @Validated @ModelAttribute("user") UserDTO userForm, BindingResult result,
            ModelMap model, HttpSession session) {
        if (result.hasErrors()) {
            model.put("userClasses", UserClassEnum.values());
            return "/admin/userForm";
        }
        if (id == null || userForm.getId() == null || !id.equals(userForm.getId())) {
            session.setAttribute("error", "user.msg.edit.wrongId");
            model.put("userClasses", UserClassEnum.values());
            return "/admin/userForm";
        }
        userService.update(userForm);
        session.setAttribute("message", "user.msg.edit.successful");
        return "redirect:/admin/users/";
    }

    @RequestMapping(value = "/admin/users/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
        UserDTO userToDelete = userService.getById(id);
        if (userToDelete == null) {
            session.setAttribute("error", "user.msg.delete.unexists");
        } else {
            userService.remove(userToDelete);
            session.setAttribute("message", "user.msg.delete.successful");
        }
        return "redirect:/admin/users";
    }
}
