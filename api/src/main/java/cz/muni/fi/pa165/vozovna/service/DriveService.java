package cz.muni.fi.pa165.vozovna.service;

import java.util.List;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;

/**
 * Drive Service
 * 
 * @author Eva Neduchalová, učo 359893
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public interface DriveService {

    /**
     * Returns Drive with given id. Returns null if drive does not exists.
     * 
     * @param id Drive ID
     * @return DriveDTO if Drive with given id exists, null otherwise.
     * @throws IllegalArgumentException Throws if given id is null.
     * @throws Throws if there is problem with service.
     * 
     */
    public DriveDTO getById(Long id);

    /**
     * Saves given drive into database.
     * 
     * @param drive Drive to save
     * @throws IllegalArgumentException Throws if given drive is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public Long create(DriveDTO drive);

    /**
     * Removes given drive from database.
     * 
     * @param drive Drive to remove
     * @throws IllegalArgumentException Throws if given drive is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public void remove(DriveDTO drive);

    /**
     * Updates given drive in database.
     * 
     * @param drive Drive to update
     * @throws IllegalArgumentException Throws if given drive is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public DriveDTO update(DriveDTO drive);

    /**
     * Find and return all drives in database.
     * 
     * @return List of all drives in database.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public List<DriveDTO> findAll();

    /**
     * Find and return drives of given user.
     * 
     * @param user User whose drives we want to be returned.
     * @return List of drives of given user.
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public List<DriveDTO> findByUser(UserDTO user);

    /**
     * Finds out whether the vehicle of given drive has currently any ongoing drive.
     * 
     * @param drive
     * @return true if vehicle of this drive has no ongoing drive. false otherwise
     */
    public boolean isVehicleFromDriveAvailable(DriveDTO drive);

}
