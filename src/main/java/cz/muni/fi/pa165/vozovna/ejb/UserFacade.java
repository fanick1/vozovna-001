/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jostri
 */
@Stateless
public class UserFacade extends AbstractFacade<EJBUser> {
	@PersistenceContext(unitName = "EJBPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UserFacade() {
		super(EJBUser.class);
	}
	
}
