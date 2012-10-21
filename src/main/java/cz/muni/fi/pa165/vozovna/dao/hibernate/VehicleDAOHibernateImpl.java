package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Vehicle DAO using Hibernate
 *
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
public class VehicleDAOHibernateImpl extends GenericDAOHibernateImpl<Vehicle, Long> implements VehicleDAO {

    /*
     * (non-Javadoc) @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#findByUserClass(int)
     */
    @Override
    public List<Vehicle> findByUserClass(UserClassEnum userClass) {
        if (this.emf == null) {
            throw new IllegalStateException("EntityManagerFactory was not set.");
        }
        final EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Vehicle> query = em.createQuery("FROM Vehicle WHERE userClass = :USERCLASS ", Vehicle.class);
        query.setParameter("USERCLASS", userClass);
        List<Vehicle> result = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return result;
    }
}
