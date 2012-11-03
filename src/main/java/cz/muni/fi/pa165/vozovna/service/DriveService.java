package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.service.exceptions.DriveServiceFailureException;
import java.util.List;

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
     * @param id    Drive ID 
     * @return DriveDTO if Drive with given id exists, null otherwise.
     * @throws IllegalArgumentException     Throws if given id is null.
     * @throws DriveServiceFailureException Throws if there is problem with service.
     *             
     */
    public DriveDTO getById(Long id);

    /**
     * Saves given drive into database.
     * 
     * @param drive     Drive to save
     * @throws IllegalArgumentException     Throws if given drive is null.
     * @throws DriveServiceFailureException Throws if there is problem with service.
     */
    public Long create(DriveDTO drive);

    /**
     * Removes given drive from database.
     * 
     * @param drive     Drive to remove
     * @throws IllegalArgumentException     Throws if given drive is null.
     * @throws DriveServiceFailureException Throws if there is problem with service.
     */
    public void remove(DriveDTO drive);

    /**
     * Updates given drive in database.
     * 
     * @param drive     Drive to update
     * @throws IllegalArgumentException     Throws if given drive is null.
     * @throws DriveServiceFailureException Throws if there is problem with service.
     */
    public DriveDTO update(DriveDTO drive);

    /**
     * Find and return all drives in database.
     * 
     * @return List of all drives in database.
     * @throws DriveServiceFailureException Throws if there is problem with service.
     */
    public List<DriveDTO> findAll();

    /**
     * Find and return drives of given user.
     * 
     * @param user      User whose drives we want to be returned.
     * @return List of drives of given user.
     * @throws IllegalArgumentException     Throws if given user is null.
     * @throws DriveServiceFailureException Throws if there is problem with service.
     */
    public List<DriveDTO> findByUser(UserDTO user);

}
