package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.service.ServiceIntervalService;
import cz.muni.fi.pa165.vozovna.service.exceptions.ServiceIntervalServiceFailureException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ServiceInterval's service
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class ServiceIntervalServiceImpl implements ServiceIntervalService {

    private ServiceIntervalDAO serviceIntervalDAO;

    @Autowired
    public void setUserDAO(ServiceIntervalDAO serviceIntervalDAO) {
        this.serviceIntervalDAO = serviceIntervalDAO;
    }
    
    @Override
    @Transactional(readOnly=true)
    public ServiceIntervalDTO getById(Long id) {
        if (id == null) {
            return null;
        }
        
        ServiceInterval interval;
        try {
            interval = serviceIntervalDAO.getById(id);
        } catch(Exception e) {
            throw new ServiceIntervalServiceFailureException("Finding service interval by ID was failded.", e);
        }
    
        return new ServiceIntervalDTO(interval);
    }

    @Override
    @Transactional
    public Long create(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        
        ServiceInterval entity = serviceInterval.toServiceInterval();
        try {
            serviceIntervalDAO.create(entity);
        } catch(Exception e) {
            throw new ServiceIntervalServiceFailureException("Service interval creation was failded.", e);
        }
        serviceInterval.fromServiceInterval(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        
        try {
            serviceIntervalDAO.remove(serviceInterval.toServiceInterval());
            serviceInterval.setId(null);
        } catch(Exception e) {
            throw new ServiceIntervalServiceFailureException("Service interval deletion was failded.", e);
        }
    }

    @Override
    @Transactional
    public ServiceIntervalDTO update(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        
        ServiceInterval entity = serviceInterval.toServiceInterval();
        try {
            serviceIntervalDAO.update(entity);
        } catch(Exception e) {
            throw new ServiceIntervalServiceFailureException("Service interval update was failded.", e);
        }    
        serviceInterval.fromServiceInterval(entity); // propagate changes
        
        return serviceInterval;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ServiceIntervalDTO> findAll() {
        // Find
        List<ServiceInterval> intervals;
        try {
            intervals = serviceIntervalDAO.findAll();
        } catch(Exception e) {
            throw new ServiceIntervalServiceFailureException("Finding all service intervals were failded.", e);
        }    
        
        // Transform result from List of Entities to List of DTOs
        List<ServiceIntervalDTO> result = new ArrayList<>();
        for(ServiceInterval entity : intervals) {
            result.add(new ServiceIntervalDTO(entity));
        }
        
        return result;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ServiceIntervalDTO> findAllByVehicle(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        
        // Find
        List<ServiceInterval> intervals;
        try {
            intervals = serviceIntervalDAO.findAllByVehicle(vehicle.toVehicle());
        } catch(Exception e) {
            throw new ServiceIntervalServiceFailureException("Finding all service intervals by vehicle were failded.", e);
        }    
        
        // Transform result from List of Entities to List of DTOs
        List<ServiceIntervalDTO> result = new ArrayList<>();
        for(ServiceInterval entity : intervals) {
            result.add(new ServiceIntervalDTO(entity));
        }
        
        return result;
    }
    
}
