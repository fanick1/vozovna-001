package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of Vehicle Service
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleDAO vehicleDAO;

    @Autowired
    public void setUserDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleDTO getById(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = vehicleDAO.getById(id);
        return new VehicleDTO(vehicle);
    }

    @Override
    @Transactional
    public Long create(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        Vehicle entity = vehicle.toVehicle();
        vehicleDAO.create(entity);
        vehicle.fromVehicle(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        if (vehicle.getId() == null) {
            throw new IllegalArgumentException("vehicle unexists");
        }
        Vehicle v = vehicleDAO.getById(vehicle.getId());
        vehicleDAO.remove(v);
        vehicle.setId(null);
    }

    @Override
    @Transactional
    public VehicleDTO update(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        if (vehicle.getId() == null) {
            throw new IllegalArgumentException("vehicle unexists");
        }
        Vehicle entity = vehicleDAO.getById(vehicle.getId());

        // copy DTO`s attributes into existing entity
        entity.setBrand(vehicle.getBrand());
        entity.setDistanceCount(vehicle.getDistanceCount());
        entity.setEngineType(vehicle.getEngineType());
        entity.setType(vehicle.getType());
        entity.setVin(vehicle.getVin());
        entity.setYearMade(vehicle.getYearMade());
        entity.setUserClass(vehicle.getUserClass());

        vehicleDAO.update(entity);
        vehicle.fromVehicle(entity);
        return vehicle;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> findAll() {
        List<Vehicle> vehicles = vehicleDAO.findAll();

        // transform from List<Vehicle> to List<VehicleDTO>
        List<VehicleDTO> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            result.add(new VehicleDTO(vehicle));
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> findByUserClass(UserClassEnum userClass) {
        if (userClass == null) {
            throw new IllegalArgumentException("userClass");
        }
        List<Vehicle> vehicles = vehicleDAO.findByUserClass(userClass);

        // transform from List<Vehicle> to List<VehicleDTO>
        List<VehicleDTO> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            result.add(new VehicleDTO(vehicle));
        }

        return result;
    }

}
