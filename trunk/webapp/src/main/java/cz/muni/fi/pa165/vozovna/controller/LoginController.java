package cz.muni.fi.pa165.vozovna.controller;

import cz.muni.fi.pa165.vozovna.service.DevelopementDataGenerator;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final Logger LGR = LoggerFactory.getLogger(LoginController.class);
	
    @Autowired
    UserService userService;
    
    @Autowired
    DevelopementDataGenerator DevelopementDataGenerator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "login";
    }

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

    @RequestMapping(value = "/defaultError", method = RequestMethod.GET)
    public String defaultError(ModelMap model) {
        return "defaultError";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(ModelMap model) {
    	return "redirect:/defaultError";
    }

    @RequestMapping(value = "/login/generate", method = RequestMethod.GET)
    public String generateLogins(ModelMap model) {
    	LGR.info("Generating data");
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
	