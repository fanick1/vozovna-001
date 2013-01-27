package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.service.DriveService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static cz.muni.fi.pa165.vozovna.entity.EntityToDTOConvertor.toDTO;
import static cz.muni.fi.pa165.vozovna.entity.EntityToDTOConvertor.applyToDTO;
import static cz.muni.fi.pa165.vozovna.entity.EntityToDTOConvertor.toEntity;
/**
 * Implementation of Drive service.
 *
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
@Service
public class DriveServiceImpl implements DriveService {

    private DriveDAO driveDAO;

    @Autowired
    public void setDriveDAO(DriveDAO driveDAO) {
        this.driveDAO = driveDAO;
    }
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    private VehicleDAO vehicleDAO;

    @Autowired
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public DriveDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null.");
        }

        Drive drive = driveDAO.getById(id);

        return  toDTO(drive);
    }

    @Override
    @Transactional
    public Long create(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        Drive entity = toEntity(drive);

        if (drive.getUserId() != null) {
            entity.setUser(userDAO.getById(drive.getUserId()));
        }
        if (drive.getVehicleId() != null) {
            entity.setVehicle(vehicleDAO.getById(drive.getVehicleId()));
        }

        driveDAO.create(entity);
        drive = applyToDTO(drive, entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        Drive entity = driveDAO.getById(drive.getId());
        if (entity == null) {
            throw new IllegalArgumentException("drive does not exist");
        }
        driveDAO.remove(entity);
        drive.setId(null);
    }

    @Override
    @Transactional
    public DriveDTO update(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        Drive entity = driveDAO.getById(drive.getId());
        if (entity == null) {
            throw new IllegalArgumentException("drive does not exist");
        }

        // copy DTO's attributes into existing entity
        VehicleDTO v = drive.getVehicle();
        if (v != null) {
            entity.setVehicle(vehicleDAO.getById(v.getId()));
        }
        UserDTO u = drive.getUser();
        if (u != null) {
            entity.setUser(userDAO.getById(u.getId()));
        }

        entity.setDistance(drive.getDistance());
        entity.setState(drive.getState());
        entity.setDateFrom(drive.getDateFrom());
        entity.setDateTo(drive.getDateTo());

        driveDAO.update(entity);

        drive = applyToDTO(drive,entity);

        return drive;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriveDTO> findAll() {
        List<Drive> drives = driveDAO.findAll();
        return convertListOfDrivesToListOfDriveDTOs(drives);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriveDTO> findByUser(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        // get user
        if (user.getId() == null) {
            throw new IllegalArgumentException("user is not stored in system");
        }
        User userEntity = userDAO.getById(user.getId());
        if (userEntity == null) {
            throw new IllegalArgumentException("nonexisting user");
        }
        // find
        List<Drive> drives = driveDAO.findByUser(userEntity);

        return convertListOfDrivesToListOfDriveDTOs(drives);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isVehicleFromDriveAvailable(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        
        Drive entity;
        if (drive.getId() == null) {
            entity = toEntity(drive);
        } else {
            entity = driveDAO.getById(drive.getId());
            if (entity == null) {
                throw new IllegalArgumentException("drive unexists");
            }
        }
        
        return driveDAO.isVehicleFromDriveAvailable(entity);
    }

    /**
     * Converts list of drives to list of drive DTOs
     *
     * @param list List of drives
     * @return List of Drive Data Transform Objects
     */
    private static List<DriveDTO> convertListOfDrivesToListOfDriveDTOs(List<Drive> drives) {
        List<DriveDTO> result = new ArrayList<>();

        for (Drive drive : drives) {
            result.add(toDTO(drive));
        }

        return result;
    }
}
