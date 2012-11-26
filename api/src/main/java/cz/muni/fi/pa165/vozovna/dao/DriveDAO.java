package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import java.util.List;

/**
 * @author Jozef Triscik
 */
public interface DriveDAO extends GenericDAO<Drive, Long> {

    /**
     * Returns Drive with given id. Returns null if drive does not exists.
     *
     * @param id
     * @return Drive if exists, null otherwise.
     * @throws IllegalArgumentException Throws if given id is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    @Override
    public Drive getById(Long id);

    /**
     * Saves given drive into database.
     *
     * @param drive Drive to save
     * @throws IllegalArgumentException Throws if given drive is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    @Override
    public void create(Drive drive);

    /**
     * Removes given drive from database.
     *
     * @param drive Drive to remove
     * @throws IllegalArgumentException Throws if given drive is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    @Override
    public void remove(Drive drive);

    /**
     * Updates given drive in database.
     *
     * @param drive Drive to update
     * @throws IllegalArgumentException Throws if given drive is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    @Override
    public void update(Drive drive);

    /**
     * Find and return all drives in database.
     *
     * @return List of all drives in database.
     * @throws IllegalStateException Throws if factory is not set.
     */
    @Override
    public List<Drive> findAll();

    /**
     * Find and return drives of given user.
     *
     * @param user User whose drives we want to be returned.
     * @return List of drives of given user.
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    public List<Drive> findByUser(User user);
}
