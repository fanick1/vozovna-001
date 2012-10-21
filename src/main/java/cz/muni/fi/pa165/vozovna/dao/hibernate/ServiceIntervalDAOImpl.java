package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author eva.neduchalova
 */
public class ServiceIntervalDAOImpl extends GenericDAOHibernateImpl<ServiceInterval, Long> implements ServiceIntervalDAO {

    @Override
    public List<ServiceInterval> findAllByVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        if (vehicle.getId() == null) {
            // pro nové vozidlo můžeme přímo vrátit prázdný list bez dotazování se do DB
            return new ArrayList<ServiceInterval>();
        }
        if (emf == null) {
            throw new IllegalStateException("Factory is not initialized!");
        }
        EntityManager em = emf.createEntityManager();
        List<ServiceInterval> result = null;
        try {
            Query query = em.createQuery("FROM " + ServiceInterval.class.getName() + " interval WHERE interval.vehicle = :vehicle");
            query.setParameter("vehicle", vehicle);
            result = (List<ServiceInterval>) query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
