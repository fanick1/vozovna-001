/**
 *  Copyright 2012 Frantisek Veverka, Eva Neduchalova, Jozef Triscik, Lukas Hajek
 *  The latest (and the greatest) version of this software can be obtained at 
 *	http://code.google.com/p/vozovna-001/
 *
 *  This file is part of Vozovna.
 *   Vozovna is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Vozovna is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Vozovna.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.muni.fi.pa165.vozovna.dao.jpa;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.entities.Drive;
import cz.muni.fi.pa165.vozovna.entities.VozovnaUser;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.hibernate.service.spi.ServiceException;

/**
 * Drive DAO using Hibernate.
 * 
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public class DriveDAOHibernateImpl implements DriveDAO {

    private EntityManagerFactory emf;
	
	public void setEntityManagerFactory(final EntityManagerFactory emf){
		if(emf == null){
			throw new IllegalArgumentException("emf");
		}
		
		this.emf = emf;
	}
    
	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#getById(java.lang.Long)
	 */
	@Override
	public Drive getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null.");
        }
		EntityManager em = emf.createEntityManager();
        
		TypedQuery<Drive> query = em.createQuery("FROM DRIVE WHERE ID = :id ", Drive.class);
		query.setParameter("id", id);
        
		Drive result = query.getSingleResult();
		em.close();
         
		return result;
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#create(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void create(Drive drive) {
        if (drive == null) {
            throw new IllegalArgumentException("Drive is null.");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try {
            em.persist(drive);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            em.close();
            throw new ServiceException("Drive creation was failed.", e);
        }
        em.close();
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#remove(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void remove(Drive drive) {
		if (drive == null) {
            throw new IllegalArgumentException("Drive is null.");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try {
            em.remove(drive);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            em.close();
            throw new ServiceException("Drive remove was failed.", e);
        }
        em.close();
	}
	

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#update(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void update(Drive drive) {
		if (drive == null) {
            throw new IllegalArgumentException("Drive is null.");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try {
            em.persist(drive);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            em.close();
            throw new ServiceException("Drive update was failed.", e);
        }
        em.close();
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#findAll()
	 */
	@Override
	public List<Drive> findAll() {
		
        EntityManager em = emf.createEntityManager();
   
		TypedQuery<Drive> query = em.createQuery("FROM DRIVE", Drive.class);
		List<Drive> result = query.getResultList(); 
		em.close();
        
		return result;

	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#findByUser(cz.muni.fi.pa165.vozovna.entities.VozovnaUser)
	 */
	@Override
	public List<Drive> findByUser(VozovnaUser user) {
		if (user == null) {
            return findAll();
        }
        EntityManager em = emf.createEntityManager();
   
		TypedQuery<Drive> query = 
                em.createQuery("FROM DRIVE WHERE USER = :user", Drive.class)
                    .setParameter("user", user);
        
		List<Drive> result = query.getResultList(); 
		em.close();
        
		return result;
	}

}
