package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eva.neduchalova
 */
@Repository("serviceIntervalDao")
public class ServiceIntervalDAOImpl extends GenericDAOHibernateImpl<ServiceInterval, Long> implements ServiceIntervalDAO {

    @Override
    @Transactional
    public List<ServiceInterval> findAllByVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        if (vehicle.getId() == null) {
            // for new vehicle we don't need to query database
            return new ArrayList<ServiceInterval>();
        }
        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + ServiceInterval.class.getName()
                + " interval WHERE interval.vehicle = :vehicle");
        query.setParameter("vehicle", vehicle);
        return (List<ServiceInterval>) query.list();
    }
}
