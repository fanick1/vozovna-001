package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.service.ServiceIntervalService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.Days;
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
    private VehicleDAO vehicleDAO;

    @Autowired
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceIntervalDTO getById(Long id) {
        if (id == null) {
            return null;
        }

        ServiceInterval interval = serviceIntervalDAO.getById(id);

        
        // check, if inspection is required
        Date lastDate = interval.getDated().get(interval.getDated().size() - 1);
        Date currentDate = new Date();

        int range = (int)( (currentDate.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24));

        ServiceIntervalDTO dto = interval.toServiceIntervalDTO();
        dto.setHasRequiredInspection(range > interval.getInspectionInterval());
            
        return dto;

    }

    @Override
    @Transactional
    public Long create(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }

        ServiceInterval entity = new ServiceInterval(serviceInterval);
        // save
        serviceIntervalDAO.create(entity);

        serviceInterval = entity.applyToServiceIntervalDTO(serviceInterval);

        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }
        ServiceInterval entity = serviceIntervalDAO.getById(serviceInterval.getId());
        if (entity == null) {
            throw new IllegalArgumentException("service interval doesn't exists");
        }
        // delete
        serviceIntervalDAO.remove(entity);

        // propagate
        serviceInterval.setId(null);
    }

    @Override
    @Transactional
    public ServiceIntervalDTO update(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }

        ServiceInterval entity = serviceIntervalDAO.getById(serviceInterval.getId());
        if (entity == null) {
            throw new IllegalArgumentException("service interval doesn't exist");
        }

        // update
        entity.setDated(serviceInterval.getDated());
        entity.setDescription(serviceInterval.getDescription());
        entity.setInspectionInterval(serviceInterval.getInspectionInterval());
        VehicleDTO associatedVehicleDTO = serviceInterval.getVehicle();
        if (associatedVehicleDTO == null) {
            throw new IllegalArgumentException("Service Interval must belong to vehicle.");
        }
        Vehicle associatedVehicle = vehicleDAO.getById(associatedVehicleDTO.getId());
        if (associatedVehicle == null) {
            throw new IllegalStateException("Vehicle doesn't exist.");
        }
        entity.setVehicle(associatedVehicle);

        // save
        serviceIntervalDAO.update(entity);

        // propagate changes
        serviceInterval = entity.applyToServiceIntervalDTO(serviceInterval);

        return serviceInterval;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceIntervalDTO> findAll() {
        List<ServiceInterval> result = serviceIntervalDAO.findAll();
        return convertListOfIntervalsToListOfIntervalDTOs(result);
    }

    /**
     * Converts list of service intervals to list of service interval DTOs
     *
     * @param list List of service intervals
     * @return List of Drive Data Transform Objects
     */
    private static List<ServiceIntervalDTO> convertListOfIntervalsToListOfIntervalDTOs(List<ServiceInterval> intervals) {
        List<ServiceIntervalDTO> result = new ArrayList<>();

        for (ServiceInterval item : intervals) {
            
            // check, if inspection is required
            Date lastDate = item.getDated().get(item.getDated().size() - 1);
            Date currentDate = new Date();
            
            int range = (int)( (currentDate.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24));
            
            ServiceIntervalDTO dto = item.toServiceIntervalDTO();
            dto.setHasRequiredInspection(range > item.getInspectionInterval());
            
            result.add(dto);
        }

        return result;
    }
}
