package cz.muni.fi.pa165.vozovna.service;

import java.util.List;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;

/**
 * 
 * @author Eva Neduchalová, učo 359893
 */
public interface DriveService {

    /**
     * Returns Drive with given id. Returns null if drive does not exists.
     * 
     * @param id
     * @return DriveDTO if Drive with given id exists, null otherwise.
     * @throws IllegalArgumentException
     *             Throws if given id is null.
     * @throws IllegalStateException
     *             Throws if factory is not set.
     */
    public DriveDTO getById(Long id);

    /**
     * Saves given drive into database.
     * 
     * @param drive
     *            Drive to save
     * @throws IllegalArgumentException
     *             Throws if given drive is null.
     * @throws IllegalStateException
     *             Throws if factory is not set.
     */
    public void create(DriveDTO drive);

    /**
     * Removes given drive from database.
     * 
     * @param drive
     *            Drive to remove
     * @throws IllegalArgumentException
     *             Throws if given drive is null.
     * @throws IllegalStateException
     *             Throws if factory is not set.
     */
    public void remove(DriveDTO drive);

    /**
     * Updates given drive in database.
     * 
     * @param drive
     *            Drive to update
     * @throws IllegalArgumentException
     *             Throws if given drive is null.
     * @throws IllegalStateException
     *             Throws if factory is not set.
     */
    public void update(DriveDTO drive);

    /**
     * Find and return all drives in database.
     * 
     * @return List of all drives in database.
     * @throws IllegalStateException
     *             Throws if factory is not set.
     */
    public List<DriveDTO> findAll();

    /**
     * Find and return drives of given user.
     * 
     * @param user
     *            User whose drives we want to be returned.
     * @return List of drives of given user.
     * @throws IllegalArgumentException
     *             Throws if given user is null.
     * @throws IllegalStateException
     *             Throws if factory is not set.
     */
    public List<DriveDTO> findByUser(UserDTO user);

}
