package cz.muni.fi.pa165.vozovna.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.ui.ModelMap;

/**
 * Aspect for handling viewing messages from session
 * 
 * @author Eva Neduchalová
 * 
 */
@Aspect
public class MessagesPrinterAspect {

    /**
     * Puts message and error object from session into model and removes them from session.
     * 
     * @param pjp
     * @param model
     * @param session
     * @return
     * @throws Throwable
     */
    @Around("execution(* cz.muni.fi.pa165.vozovna.controller..*.*(..)) && args( .., model, session)")
    public Object onAction(ProceedingJoinPoint pjp, ModelMap model, HttpSession session) throws Throwable {

        if (model != null && session != null) {
            model.put("message", (String) session.getAttribute("message"));
            session.setAttribute("message", null);
            model.put("error", (String) session.getAttribute("error"));
            session.setAttribute("error", null);
        }

        return pjp.proceed();
    }

}
