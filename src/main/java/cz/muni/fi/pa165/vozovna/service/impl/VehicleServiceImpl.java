package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
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
        Vehicle vehicle = vehicleDAO.getById(id);
        
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.fromVehicle(vehicle);
        
        return vehicleDTO;
    }

    @Override
    @Transactional
    public void create(VehicleDTO vehicle) {
       vehicleDAO.create(vehicle.toVehicle());
    }

    @Override
    @Transactional
    public void remove(VehicleDTO vehicle) {
        vehicleDAO.remove(vehicle.toVehicle());
    }

    @Override
    @Transactional
    public void update(VehicleDTO vehicle) {
        vehicleDAO.update(vehicle.toVehicle());
    }

    @Override
    @Transactional(readOnly=true)
    public List<VehicleDTO> findAll() {
      
        List<Vehicle> vehicles = vehicleDAO.findAll();
        
        // transform from List<Vehicle> to List<VehicleDTO>
        List<VehicleDTO> result = new ArrayList<>();
        for(Vehicle vehicle:vehicles) {
            VehicleDTO dto = new VehicleDTO();
            dto.fromVehicle(vehicle);
            result.add(dto);
        }
        
        return result;
    }

    @Override
    @Transactional(readOnly=true)
    public List<VehicleDTO> findByUserClass(UserClassEnum userClass) {
        
        List<Vehicle> vehicles = vehicleDAO.findByUserClass(userClass);
        
        List<VehicleDTO> result = new ArrayList<>();
        for(Vehicle vehicle:vehicles) {
            VehicleDTO dto = new VehicleDTO();
            dto.fromVehicle(vehicle);
            result.add(dto);
        }
        
        return result;
    }

   
    
}
