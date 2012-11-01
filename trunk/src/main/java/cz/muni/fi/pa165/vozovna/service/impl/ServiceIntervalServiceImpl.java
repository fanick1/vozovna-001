package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.service.ServiceIntervalService;
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
        ServiceInterval interval = serviceIntervalDAO.getById(id);
        ServiceIntervalDTO serviceIntervalDTO = new ServiceIntervalDTO();
        serviceIntervalDTO.fromServiceInterval(interval);
        
        return serviceIntervalDTO;
    }

    @Override
    @Transactional
    public void create(ServiceIntervalDTO serviceInterval) {
        serviceIntervalDAO.create(serviceInterval.toServiceInterval());
    }

    @Override
    @Transactional
    public void remove(ServiceIntervalDTO serviceInterval) {
        serviceIntervalDAO.remove(serviceInterval.toServiceInterval());
    }

    @Override
    @Transactional
    public void update(ServiceIntervalDTO serviceInterval) {
        serviceIntervalDAO.update(serviceInterval.toServiceInterval());
    }

    @Override
    @Transactional(readOnly=true)
    public List<ServiceIntervalDTO> findAll() {
        List<ServiceInterval> intervals = serviceIntervalDAO.findAll();
        
        List<ServiceIntervalDTO> result = new ArrayList<>();
        for(ServiceInterval serviceInterval : intervals) {
            ServiceIntervalDTO dto = new ServiceIntervalDTO();
            dto.fromServiceInterval(serviceInterval);
            result.add(dto);
        }
        
        return result;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ServiceIntervalDTO> findAllByVehicle(VehicleDTO vehicle) {
        List<ServiceInterval> intervals = serviceIntervalDAO.findAllByVehicle(vehicle.toVehicle());
        
        List<ServiceIntervalDTO> result = new ArrayList<>();
        for(ServiceInterval serviceInterval : intervals) {
            ServiceIntervalDTO dto = new ServiceIntervalDTO();
            dto.fromServiceInterval(serviceInterval);
            result.add(dto);
        }
        
        return result;
    }
    
}
