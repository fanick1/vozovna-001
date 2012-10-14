/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.dao.jpa;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.entities.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author eva.neduchalova
 */
public class ServiceIntervalDaoImpl implements ServiceIntervalDAO {

    private EntityManagerFactory factory;
    
    public void setFactory(EntityManagerFactory factory) {
        if(factory == null) {
            throw new IllegalArgumentException("Given factory cannot be null");
        }
        
        this.factory = factory;
    }
    
    @Override
    public ServiceInterval getById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create(ServiceInterval serviceInterval) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(ServiceInterval serviceInterval) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ServiceInterval> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(ServiceInterval serviceInterval) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ServiceInterval> findAllByVehicle(Vehicle vehicle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
