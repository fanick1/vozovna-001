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
package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.entities.Drive;
import cz.muni.fi.pa165.vozovna.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * Drive DAO using Hibernate.
 * 
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public class DriveDAOHibernateImpl implements DriveDAO {

    private EntityManagerFactory emf;
	
    /**
     * Sets Entity Manager Factory
     * @param emf   The Entity Manager Factory
     * @throws IllegalArgumentException     If emf is null.
     */
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
        if (this.emf == null) {
            throw new IllegalStateException("Entity Manager Factory is not set.");
        }
		EntityManager em = this.emf.createEntityManager();
        Drive result = null;
        try {

            result = em.find(Drive.class, id);
        } catch(RuntimeException e) {
            throw e;
        } finally {
            em.close();
        }
        
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
        if (this.emf == null) {
            throw new IllegalStateException("Entity Manager Factory is not set.");
        }
        EntityManager em = this.emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        transaction.begin();
        try {
            em.persist(drive);
            transaction.commit();
        } catch(RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
        
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#remove(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void remove(Drive drive) {
		if (drive == null) {
            throw new IllegalArgumentException("Drive is null.");
        }
        if (this.emf == null) {
            throw new IllegalStateException("Entity Manager Factory is not set.");
        }
        EntityManager em = this.emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        transaction.begin();
       
        try {
            em.remove(drive);
            transaction.commit();
        } catch(RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
        
	}
	

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#update(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void update(Drive drive) {
		if (drive == null) {
            throw new IllegalArgumentException("Drive is null.");
        }
        if (this.emf == null) {
            throw new IllegalStateException("Entity Manager Factory is not set.");
        }
        EntityManager em = this.emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
       
        transaction.begin();
        
        try {
            em.persist(drive);
            transaction.commit();
        } catch(RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
        
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#findAll()
	 */
	@Override
	public List<Drive> findAll() {
		if (this.emf == null) {
            throw new IllegalStateException("Entity Manager Factory is not set.");
        }
        
        EntityManager em = this.emf.createEntityManager();
        List<Drive> result = null;
        
		try {
            TypedQuery<Drive> query = em.createQuery("FROM DRIVE", Drive.class);
            result = query.getResultList(); 
        } catch (RuntimeException e) {
            throw e;
        } finally {
            em.close();
        }
        
		return result;

	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#findByUser(cz.muni.fi.pa165.vozovna.entities.VozovnaUser)
	 */
	@Override
	public List<Drive> findByUser(User user) {
		if(user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        if (this.emf == null) {
            throw new IllegalStateException("Entity Manager Factory is not set.");
        }
        EntityManager em = this.emf.createEntityManager();
        List<Drive> result = null;
        
        try {
            TypedQuery<Drive> query = 
                em.createQuery("FROM DRIVE WHERE USER = :user", Drive.class)
                    .setParameter("user", user);
            result = query.getResultList(); 
        } catch (RuntimeException e) {
            throw e;
        } finally {
            em.close();
        }
        
		return result;
	}

}
