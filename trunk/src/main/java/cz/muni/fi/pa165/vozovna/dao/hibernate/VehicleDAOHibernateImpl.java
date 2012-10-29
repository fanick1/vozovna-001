package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

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
        TypedQuery<Vehicle> query = em.createQuery("FROM " + Vehicle.class.getName() + " WHERE userClass = :USERCLASS ", Vehicle.class);
        query.setParameter("USERCLASS", userClass);
        List<Vehicle> result = query.getResultList();
        return result;
    }
}
