package cz.muni.fi.pa165.vozovna.aspect;

import javax.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.ui.ModelMap;

/**
 * Aspect for handling viewing information and error messages from session
 *
 * @author Eva Neduchalová
 *
 */
@Aspect
public class MessagesPrinterAspect {

    /**
     * Puts message and error object from session into model and removes them from session afterwards.
     *
     * @param pjp join point to be intercepted
     * @param model model to put the message into
     * @param session current session
     */
    @Before("execution(* cz.muni.fi.pa165.vozovna.controller..*.*(..)) && args( .., model, session)")
    public void onAction(ProceedingJoinPoint pjp, ModelMap model, HttpSession session) {

        if (model != null && session != null) {
            model.put("message", (String) session.getAttribute("message"));
            session.setAttribute("message", null);
            model.put("error", (String) session.getAttribute("error"));
            session.setAttribute("error", null);
        }

    }
}
