package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
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

    @Autowired
    private VehicleDAO vehicleDAO;

//    public void setUserDAO(VehicleDAO vehicleDAO) {
//        this.vehicleDAO = vehicleDAO;
//    }
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

        //  copy DTO`s attributes into existing entity
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

        return convertListOfVehiclesToListOfVehicleDTOs(vehicles);
    }

    /**
     * Converts list of vehicles to list of vehicle DTOs
     *
     * @param list List of vehicles
     * @return List of Vehicle Data Transform Objects
     */
    private static List<VehicleDTO> convertListOfVehiclesToListOfVehicleDTOs(List<Vehicle> intervals) {
        List<VehicleDTO> result = new ArrayList<>();

        for (Vehicle item : intervals) {
            result.add(new VehicleDTO(item));
        }

        return result;
    }

    /**
     * Returns available cars for uesr between given dates.
     *
     * @param user User, for which we want cars.
     * @param startDate Date, from which we want reservate car.
     * @param endDate Date, to which we want reservate car.
     *
     * @throws IllegalArgumentException If any of argument is null.
     */
    @Override
    public List<VehicleDTO> getAvailableVehicles(User user, DateTime startDate, DateTime endDate) {
        return convertListOfVehiclesToListOfVehicleDTOs(this.vehicleDAO.getAvailableVehicles(user, startDate, endDate));


    }

    @Override
    public List<VehicleDTO> getAvailableVehicles(UserClassEnum userClass, DateTime startDate, DateTime endDate) {
        return convertListOfVehiclesToListOfVehicleDTOs(this.vehicleDAO.getAvailableVehicles(userClass, startDate, endDate));
    }
}
