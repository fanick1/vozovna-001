package cz.muni.fi.pa165.vozovna.controller;

import cz.muni.fi.pa165.vozovna.service.DevelopementDataGenerator;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    
    @Autowired
    DevelopementDataGenerator DevelopementDataGenerator;
    
    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String redirectToWelcomePage(HttpServletRequest request, ModelMap model, Principal principal) {
        if (principal == null) {
            return "redirect:login";
        }
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/users";
        } else {
            return "redirect:drives";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/login/generate", method = RequestMethod.GET)
    public String generateLogins(ModelMap model) {
        DevelopementDataGenerator.generateTestDataIfNoneExist();
        return "redirect:/login";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "login";
    }
}
