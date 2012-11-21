package cz.muni.fi.pa165.vozovna.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.ui.ModelMap;

/**
 * @author Eva Neduchalová
 * 
 */
@Aspect
public class MessagesPrinterAspect {

    // @Around("execution(*cz.muni.fi.pa165.vozovna.controller.admin.UsersController.*(..)) && args(model, session) )")
    @Around("execution(* cz.muni.fi.pa165.vozovna.controller.admin.UsersController.*(..)) && args( .., model, session)")
    public Object onAction(ProceedingJoinPoint pjp, ModelMap model, HttpSession session) throws Throwable {

        if (model != null && session != null) {
            model.put("message", (String) session.getAttribute("message"));
            session.setAttribute("message", null);
            model.put("error", (String) session.getAttribute("error"));
            session.setAttribute("error", null);
        }

        Object result = pjp.proceed();

        return result;
    }

}
