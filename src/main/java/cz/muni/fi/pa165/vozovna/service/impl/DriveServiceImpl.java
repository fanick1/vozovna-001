package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.service.DriveService;
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
        Drive drive = driveDAO.getById(id);
        
        DriveDTO dto = new DriveDTO();
        dto.fromDrive(drive);
        
        return dto;
    }

    @Override
    @Transactional
    public void create(DriveDTO drive) {
        driveDAO.create(drive.toDrive());        
    }

    @Override
    @Transactional
    public void remove(DriveDTO drive) {
        driveDAO.remove(drive.toDrive()); 
    }

    @Override
    @Transactional
    public void update(DriveDTO drive) {
       driveDAO.update(drive.toDrive()); 
    }

    @Override
    @Transactional(readOnly=true)
    public List<DriveDTO> findAll() {
        List<Drive> drives = driveDAO.findAll();
        
        return convertListOfDrivesToListOfDriveDTOs(drives); 
    }

    @Override
    @Transactional(readOnly=true)
    public List<DriveDTO> findByUser(UserDTO user) {
        List<Drive> drives = driveDAO.findByUser(user.toUser());
        
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
            DriveDTO dto = new DriveDTO();
            dto.fromDrive(drive);
            result.add(dto);
        }
        
        return result; 
   }
    
}
