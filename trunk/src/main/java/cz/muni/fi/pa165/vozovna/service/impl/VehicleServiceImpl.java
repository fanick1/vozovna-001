package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import cz.muni.fi.pa165.vozovna.service.exceptions.VehicleServiceFailureException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of Vehicle Service
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class VehicleServiceImpl implements VehicleService {

    private VehicleDAO vehicleDAO;

    @Autowired
    public void setUserDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
    
    @Override
    @Transactional(readOnly=true)
    public VehicleDTO getById(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle;
        try {
            vehicle = vehicleDAO.getById(id);
        } catch(Exception e) {
            throw new VehicleServiceFailureException("Finding vehicle by ID was failded.", e);
        }

        return new VehicleDTO(vehicle);
    }

    @Override
    @Transactional
    public Long create(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        
        Vehicle entity = vehicle.toVehicle();
        try {
            vehicleDAO.create(entity);
        } catch(Exception e) {
            throw new VehicleServiceFailureException("Vehicle creation was failded.", e);
        }
        
        vehicle.fromVehicle(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        try {
            vehicleDAO.remove(vehicle.toVehicle());
            vehicle.setId(null);
        } catch(Exception e) {
            throw new VehicleServiceFailureException("Vehicle deletion was failded.", e);
        }
    }

    @Override
    @Transactional
    public VehicleDTO update(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        
        Vehicle entity = vehicle.toVehicle();
        try {
            vehicleDAO.update(entity);
        } catch(Exception e) {
            throw new VehicleServiceFailureException("Vehicle update was failded.", e);
        }
        vehicle.fromVehicle(entity);
        
        return vehicle;
    }

    @Override
    @Transactional(readOnly=true)
    public List<VehicleDTO> findAll() {
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleDAO.findAll();
        } catch(Exception e) {
            throw new VehicleServiceFailureException("Finding all vehicles was failded.", e);
        }
        
        // transform from List<Vehicle> to List<VehicleDTO>
        List<VehicleDTO> result = new ArrayList<>();
        for(Vehicle vehicle:vehicles) {
            result.add(new VehicleDTO(vehicle));
        }
        
        return result;
    }

    @Override
    @Transactional(readOnly=true)
    public List<VehicleDTO> findByUserClass(UserClassEnum userClass) {
        if (userClass == null) {
            throw new IllegalArgumentException("userClass");
        }
        List<Vehicle> vehicles;
        try {
            vehicles = vehicleDAO.findByUserClass(userClass);
        } catch(Exception e) {
            throw new VehicleServiceFailureException("Finding all vehicles by user class was failded.", e);
        }
        
        // transform from List<Vehicle> to List<VehicleDTO>
        List<VehicleDTO> result = new ArrayList<>();
        for(Vehicle vehicle:vehicles) {
            result.add(new VehicleDTO(vehicle));
        }
        
        return result;
    }

   
    
}
