package cz.muni.fi.pa165.vozovna.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.muni.fi.pa165.vozovna.service.DriveService;

@Controller
public class UserDrivesController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    DriveService driveService;

    @RequestMapping(value = "/drives", method = RequestMethod.GET)
    public String userDrives(HttpServletRequest request, ModelMap model, Principal principal) {

        //model.put("drives", driveService.findByUser(user));
        logger.debug("tajcto");

        return "drives";
    }

}
