package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import java.util.List;

/**
 * @author Jozef Triscik
 */
public interface DriveDAO extends GenericDAO<Drive, Long> {


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
