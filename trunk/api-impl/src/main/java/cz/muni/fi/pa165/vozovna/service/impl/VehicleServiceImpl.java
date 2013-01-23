package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private DriveDAO driveDAO;
    
    @Autowired
    private ServiceIntervalDAO serviceIntervalDAO;

    @Override
    @Transactional(readOnly = true)
    public VehicleDTO getById(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = vehicleDAO.getById(id);
        
        VehicleDTO vehicleDTO = vehicle.toVehicleDTO();
        
        List<ServiceIntervalDTO> intervals = new ArrayList<>();
        
        for(ServiceInterval interval: this.serviceIntervalDAO.findAllByVehicle(vehicle)) {
            ServiceIntervalDTO dto = interval.toServiceIntervalDTO();
            
            // check, if inspection is required
            Date lastDate = dto.getDated().get(dto.getDated().size() - 1);
            Date currentDate = new Date();
            
            int range = (int)( (currentDate.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24));
            
            dto.setHasRequiredInspection(range > dto.getInspectionInterval());
            
            intervals.add(dto);
        }
       
        vehicleDTO.setServiceIntervals(intervals);
        
        vehicleDTO.setCanRemove(this.canRemoveVehicle(vehicle));
        vehicleDTO.setMileage(this.vehicleDAO.getMileageOfVehicle(vehicle));
        
        return vehicleDTO;
    }

    @Override
    @Transactional
    public Long create(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("null vehicle");
        }
        Vehicle entity = new Vehicle(vehicle);
        vehicleDAO.create(entity);
        vehicle = entity.applyToVehicleDTO(vehicle);
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
        entity.setRegistrationPlate(vehicle.getRegistrationPlate());

        vehicleDAO.update(entity);
        vehicle = entity.applyToVehicleDTO(vehicle);
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
    private List<VehicleDTO> convertListOfVehiclesToListOfVehicleDTOs(List<Vehicle> vehicles) {
        List<VehicleDTO> result = new ArrayList<>();

        for (Vehicle item : vehicles) {
            VehicleDTO vehicleDTO = item.toVehicleDTO();
            
            vehicleDTO.setCanRemove(this.canRemoveVehicle(item));
            vehicleDTO.setMileage(this.vehicleDAO.getMileageOfVehicle(item));
            
            result.add(vehicleDTO);
        }

        return result;
    }
    
    /**
     * Checks, if there exists future drives with given vehicle and if no, return true.
     * 
     * @param vehicle vehicle, for which check drives
     * @return If true, vehicle don't have drives, so can be deleted.
     */
    private boolean canRemoveVehicle(Vehicle vehicle) {
        if(vehicle == null) {
            throw new IllegalArgumentException("vehicle is null");
        }
        
        return !this.driveDAO.hasVehicleDrives(vehicle);
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
    public List<VehicleDTO> getAvailableVehicles(UserDTO user, DateTime startDate, DateTime endDate) {
        return convertListOfVehiclesToListOfVehicleDTOs(this.vehicleDAO.getAvailableVehicles(new User(user), startDate, endDate));


    }

    @Override
    public List<VehicleDTO> getAvailableVehicles(UserClassEnum userClass, DateTime startDate, DateTime endDate) {
        return convertListOfVehiclesToListOfVehicleDTOs(this.vehicleDAO.getAvailableVehicles(userClass, startDate, endDate));
    }
}
