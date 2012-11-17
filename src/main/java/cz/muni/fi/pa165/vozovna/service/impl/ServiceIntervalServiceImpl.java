package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.service.ServiceIntervalService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ServiceInterval's service
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
@Service
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
        
        ServiceInterval interval = serviceIntervalDAO.getById(id);
    
        return new ServiceIntervalDTO(interval);
    }

    @Override
    @Transactional
    public Long create(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        
        ServiceInterval entity = serviceInterval.toServiceInterval();
        
        serviceIntervalDAO.create(entity);
        
        serviceInterval.fromServiceInterval(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        
        serviceIntervalDAO.remove(serviceInterval.toServiceInterval());
        serviceInterval.setId(null);
    }

    @Override
    @Transactional
    public ServiceIntervalDTO update(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        
        ServiceInterval entity = serviceInterval.toServiceInterval();
        
        serviceIntervalDAO.update(entity);
        
        serviceInterval.fromServiceInterval(entity); // propagate changes
        
        return serviceInterval;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ServiceIntervalDTO> findAll() {
        // Find
        List<ServiceInterval> intervals = serviceIntervalDAO.findAll();
                
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
        List<ServiceInterval> intervals = serviceIntervalDAO.findAllByVehicle(vehicle.toVehicle());
                
        // Transform result from List of Entities to List of DTOs
        List<ServiceIntervalDTO> result = new ArrayList<>();
        for(ServiceInterval entity : intervals) {
            result.add(new ServiceIntervalDTO(entity));
        }
        
        return result;
    }
    
}
