package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.service.ServiceIntervalService;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
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

        return new ServiceIntervalDTO(interval);
    }

    @Override
    @Transactional
    public Long create(ServiceIntervalDTO serviceInterval) {
        if (serviceInterval == null) {
            throw new IllegalArgumentException("null serviceInterval");
        }

        ServiceInterval entity = serviceInterval.toServiceInterval();
        // save
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
            throw new IllegalArgumentException("Service Interval have to become to vehicle.");
        }
        Vehicle associatedVehicle = vehicleDAO.getById(associatedVehicleDTO.getId());
        if (associatedVehicle == null) {
            throw new IllegalStateException("Vehicle doesn't exist.");
        }
        entity.setVehicle(associatedVehicle);

        // save
        serviceIntervalDAO.update(entity);

        // propagate changes
        serviceInterval.fromServiceInterval(entity);

        return serviceInterval;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceIntervalDTO> findAll() {
        // find
        List<ServiceInterval> result = serviceIntervalDAO.findAll();

        return convertListOfIntervalsToListOfIntervalDTOs(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceIntervalDTO> findAllByVehicle(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }

        // find
        List<ServiceInterval> result = serviceIntervalDAO.findAllByVehicle(vehicle.toVehicle());

        return convertListOfIntervalsToListOfIntervalDTOs(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceIntervalDTO> findByCriteria(List<Criterion> criterion, List<Order> orders) {

        List<ServiceInterval> result = serviceIntervalDAO.findByCriteria(criterion, orders);

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
            result.add(new ServiceIntervalDTO(item));
        }

        return result;
    }
}
