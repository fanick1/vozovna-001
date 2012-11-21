package cz.muni.fi.pa165.vozovna.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.service.DriveService;

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

    @Override
    @Transactional(readOnly = true)
    public DriveDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null.");
        }

        Drive drive = driveDAO.getById(id);

        return new DriveDTO(drive);
    }

    @Override
    @Transactional
    public Long create(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }
        Drive entity = drive.toDrive();

        driveDAO.create(entity);

        drive.fromDrive(entity);

        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }

        driveDAO.remove(drive.toDrive());
        drive.setId(null);
    }

    @Override
    @Transactional
    public DriveDTO update(DriveDTO drive) {
        if (drive == null) {
            throw new IllegalArgumentException("null drive");
        }

        Drive entity = drive.toDrive();

        driveDAO.update(entity);

        drive.fromDrive(entity);

        return drive;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriveDTO> findAll() {
        List<Drive> drives;

        drives = driveDAO.findAll();

        return convertListOfDrivesToListOfDriveDTOs(drives);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriveDTO> findByUser(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() == null) {
            return new ArrayList<DriveDTO>();
        }

        List<Drive> drives = driveDAO.findByUser(userDAO.getById(user.getId()));

        return convertListOfDrivesToListOfDriveDTOs(drives);
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
            result.add(new DriveDTO(drive));
        }

        return result;
    }

}
