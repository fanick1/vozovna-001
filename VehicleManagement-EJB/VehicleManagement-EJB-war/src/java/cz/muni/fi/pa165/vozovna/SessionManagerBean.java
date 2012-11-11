/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author jostri
 */
@Singleton
@LocalBean
@WebListener
public class SessionManagerBean implements HttpSessionListener  {

	private static int counter = 0;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		SessionManagerBean.counter++;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		SessionManagerBean.counter--;
	}

	public int getActiveSessionsCount() {
        return SessionManagerBean.counter;
    }

}
