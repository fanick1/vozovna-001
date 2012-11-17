package cz.muni.fi.pa165.vozovna.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.muni.fi.pa165.vozovna.service.VehicleService;

@Controller
public class VehiclesController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    VehicleService vehicleService;

    @RequestMapping(value = "/admin/vehicles", method = RequestMethod.GET)
    public String printWelcome(HttpServletRequest request, ModelMap model) {

        // model.put("drives", driveService.findByUser(user));
        logger.debug("tajcto");

        return "/admin/vehicles";
    }

}