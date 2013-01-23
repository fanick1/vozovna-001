/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.ejb;

import javax.annotation.Resource;
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
@MessageDriven(mappedName = "jms/UserMessage", activationConfig = {
	@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class UserMessage implements MessageListener {
	
    @Resource
	private MessageDrivenContext mdc;
	
	@PersistenceContext(unitName = "EJBPU")
	private EntityManager em;
	
	public UserMessage() {
	}
	
	@Override
	public void onMessage(Message message) {
		
		ObjectMessage msg;
		try {
			if(message instanceof ObjectMessage) {
				msg = (ObjectMessage)message;
				EJBUser user = (EJBUser)msg.getObject();
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