package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.entities.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * @author eva.neduchalova, 359893
 */
public class ServiceIntervalDaoImpl implements ServiceIntervalDAO {

    private EntityManagerFactory emf;

    public void setFactory(EntityManagerFactory emf) {
        if (emf == null) {
            throw new IllegalArgumentException("Given factory cannot be null");
        }
        this.emf = emf;
    }

    @Override
    public ServiceInterval getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("null id");
        }
        EntityManager em = emf.createEntityManager();
        ServiceInterval result = null;
        try {
            result = em.find(ServiceInterval.class, id);
        } finally {
            em.close();
        }
        return result;
    }

    @Override
    public void create(ServiceInterval serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(serviceInterval);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void update(ServiceInterval serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(serviceInterval);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(ServiceInterval serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(serviceInterval);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public List<ServiceInterval> findAll() {
        EntityManager em = emf.createEntityManager();
        List<ServiceInterval> result = null;
        try {
            Query query = em.createQuery("SELECT interval FROM " + ServiceInterval.class + " interval");
            result = (List<ServiceInterval>) query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    @Override
    public List<ServiceInterval> findAllByVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        if (vehicle.getId() == null) {
            // pro nové vozidlo můžeme přímo vrátit prázdný list bez dotazování se do DB
            return new ArrayList<ServiceInterval>();
        }
        EntityManager em = emf.createEntityManager();
        List<ServiceInterval> result = null;
        try {
            Query query = em.createQuery("SELECT interval FROM " + ServiceInterval.class + " interval "
                    + "WHERE interval.vehicle = :vehicle");
            query.setParameter("vehicle", vehicle);
            result = (List<ServiceInterval>) query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
