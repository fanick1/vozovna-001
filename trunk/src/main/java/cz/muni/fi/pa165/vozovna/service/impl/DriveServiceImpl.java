package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.service.DriveService;
import cz.muni.fi.pa165.vozovna.service.exceptions.DriveServiceFailureException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of Drive service.
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class DriveServiceImpl implements DriveService {

    private DriveDAO driveDAO;

    @Autowired
    public void setDriveDAO(DriveDAO driveDAO) {
        this.driveDAO = driveDAO;
    }
    
    @Override
    @Transactional(readOnly=true)
    public DriveDTO getById(Long id) {
        if (id == null) {
            return null;
        }
        
        Drive drive;
        try {
            drive = driveDAO.getById(id);
        } catch(Exception e) {
            throw new DriveServiceFailureException("Finding drive by ID was failded.", e);
        }

        return new DriveDTO(drive);
    }

    @Override
    @Transactional
    public Long create(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        Drive entity = drive.toDrive();
        try {
            driveDAO.create(entity);     
        } catch(Exception e) {
            throw new DriveServiceFailureException("Drive creation was failded.", e);
        }
        drive.fromDrive(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        
        try {
            driveDAO.remove(drive.toDrive()); 
            drive.setId(null);
        } catch(Exception e) {
            throw new DriveServiceFailureException("Drive deletion was failed.", e);
        }
      
    }

    @Override
    @Transactional
    public DriveDTO update(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        
        Drive entity = drive.toDrive();
        try {
            driveDAO.update(entity); 
        } catch(Exception e) {
            throw new DriveServiceFailureException("Drive update was failed.", e);
        }
        drive.fromDrive(entity);
        
        return drive;
    }

    @Override
    @Transactional(readOnly=true)
    public List<DriveDTO> findAll() {
        List<Drive> drives;
        try {
            drives = driveDAO.findAll();
        } catch(Exception e) {
            throw new DriveServiceFailureException("Finding all drives were failed.", e);
        }
        return convertListOfDrivesToListOfDriveDTOs(drives); 
    }

    @Override
    @Transactional(readOnly=true)
    public List<DriveDTO> findByUser(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        
        List<Drive> drives;
        try {
            drives = driveDAO.findByUser(user.toUser());
        } catch(Exception e) {
            throw new DriveServiceFailureException("Finding all drives by user were failed.", e);
        }
        return convertListOfDrivesToListOfDriveDTOs(drives); 
    }

    /**
     * Converts list of drives to list of drive DTOs
     * @param list      List of drives
     * @return          List of Drive Data Transform Objects
     */
    private static List<DriveDTO> convertListOfDrivesToListOfDriveDTOs(List <Drive> drives) {
        List<DriveDTO> result = new ArrayList<>();
        
        for(Drive drive : drives) {
            result.add(new DriveDTO(drive));
        }
        
        return result; 
   }
    
}
