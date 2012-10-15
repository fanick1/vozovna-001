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

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * Vehicle DAO using Hibernate
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
public class VehicleDAOHibernateImpl implements VehicleDAO {

	private EntityManagerFactory emf;
	
	public void setEntityManagerFactory(final EntityManagerFactory emf){
		if(emf == null){
			throw new IllegalArgumentException("EntityManagerFactory instance is null");
		}
		
		this.emf = emf;
	}
	
	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#getById(java.lang.Long)
	 */
	@Override
	public Vehicle getById(Long id) {
		if(id == null){
			throw new IllegalArgumentException("Id is null");
		}
		if(this.emf == null){
			throw new IllegalStateException("EntityManagerFactory was not set.");
		}
		final EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Vehicle> query = em.createQuery("FROM Vehicle WHERE ID = :id ", Vehicle.class) ;
		query.setParameter("id", id);
		Vehicle result = query.getSingleResult(); 
		em.getTransaction().commit();
		em.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#add(cz.muni.fi.pa165.vozovna.entities.Vehicle)
	 */
	@Override
	public void create(final Vehicle vehicle) {
		if(vehicle == null){
			throw new IllegalArgumentException("Vehicle is null");
		}
		
		if(this.emf == null){
			throw new IllegalStateException("EntityManagerFactory was not set.");
		}
		
		final EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(vehicle);
		em.getTransaction().commit();
		em.close();
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#remove(cz.muni.fi.pa165.vozovna.entities.Vehicle)
	 */
	@Override
	public void remove(final Vehicle vehicle) {
		if(vehicle == null){
			throw new IllegalArgumentException("Vehicle is null");
		}
		if(this.emf == null){
			throw new IllegalStateException("EntityManagerFactory was not set.");
		}

		final EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(vehicle));
		em.getTransaction().commit();
		em.close();
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#update(cz.muni.fi.pa165.vozovna.entities.Vehicle)
	 */
	@Override
	public void update(final Vehicle vehicle) {
		if(vehicle == null){
			throw new IllegalArgumentException("Vehicle is null");
		}
		
		if(this.emf == null){
			throw new IllegalStateException("EntityManagerFactory was not set.");
		}
		
		final EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(em.merge(vehicle));
		em.getTransaction().commit();
		em.close();
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#findAll()
	 */
	@Override
	public List<Vehicle> findAll() {
		if(this.emf == null){
			throw new IllegalStateException("EntityManagerFactory was not set.");
		}

		final EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();
		List<Vehicle> result = em.createQuery("FROM Vehicle", Vehicle.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#findByUserClass(int)
	 */
	@Override
	public List<Vehicle> findByUserClass(UserClassEnum userClass) {
		if(this.emf == null){
			throw new IllegalStateException("EntityManagerFactory was not set.");
		}
		final EntityManager em = this.emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Vehicle> query = em.createQuery("FROM Vehicle WHERE userClass = :USERCLASS ", Vehicle.class) ;
		query.setParameter("USERCLASS", userClass);
		List<Vehicle> result = query.getResultList(); 
		em.getTransaction().commit();
		em.close();
		return result;
	}

}
