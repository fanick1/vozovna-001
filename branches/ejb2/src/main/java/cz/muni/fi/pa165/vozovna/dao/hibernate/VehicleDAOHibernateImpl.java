package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * Vehicle DAO using Hibernate
 * 
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@Repository("vehicleDao")
public class VehicleDAOHibernateImpl extends GenericDAOHibernateImpl<Vehicle, Long> implements VehicleDAO {

    /*
     * (non-Javadoc) @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#findByUserClass(int)
     */
    @Override
    @Transactional
    public List<Vehicle> findByUserClass(UserClassEnum userClass) {
        if (userClass == null) {
            throw new IllegalArgumentException("userClass is null.");
        }

        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + Vehicle.class.getName() + " WHERE userClass = :USERCLASS ");
        query.setParameter("USERCLASS", userClass);
        return (List<Vehicle>) query.list();

    }

}
