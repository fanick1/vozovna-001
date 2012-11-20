package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entity.User;
import java.util.List;

/**
 *
 * @author eva.neduchalova
 */
public interface UserDAO extends GenericDAO<User, Long> {

    /**
     * Returns all users with given name.
     *
     * @param name Name of required user
     * @return List of users, with given name
     * @throws IllegalArgumentException Throws if given name is null.
     * @throws IllegalStateException Throws if factory is not initialized.
     */
    List<User> findByLastName(String lastName);

    /**
     * Returns User with given username. Returns null if such user does not exist.
     * 
     * @param username username of demanded user
     * @return User if exists, null otherwise.
     * @throws IllegalArgumentException Throws if given username is null.
     */
    User getByUsername(String username);
}
