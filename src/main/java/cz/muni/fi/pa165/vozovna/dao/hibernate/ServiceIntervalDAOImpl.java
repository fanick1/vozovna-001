package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;

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
            // pro nové vozidlo můžeme přímo vrátit prázdný list bez dotazování se do DB
            return new ArrayList<ServiceInterval>();
        }
        List<ServiceInterval> result = null;
        Query query = em.createQuery("FROM " + ServiceInterval.class.getName() + " interval WHERE interval.vehicle = :vehicle");
        query.setParameter("vehicle", vehicle);
        result = (List<ServiceInterval>) query.getResultList();
        return result;
    }
}
