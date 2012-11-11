/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jostri
 */
@MessageDriven(mappedName = "jms/User", activationConfig = {
	@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessage implements MessageListener {
	
	private MessageDrivenContext mdc;
	
	@PersistenceContext(unitName = "VehicleManagement-EJB-ejbPU")
	private EntityManager em;
	
	public NewMessage() {
	}
	
	@Override
	public void onMessage(Message message) {
		
		ObjectMessage msg;
		try {
			if(message instanceof ObjectMessage) {
				msg = (ObjectMessage)message;
				User user = (User)msg.getObject();
				this.save(user);
			}
		} catch (JMSException e) {
        mdc.setRollbackOnly();
		} catch (Throwable te) {
		}
		
	}

	public void save(Object object) {
		em.persist(object);
	}
}
