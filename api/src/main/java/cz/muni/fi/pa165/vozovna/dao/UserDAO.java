package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import java.util.List;

/**
 *
 * @author eva.neduchalova
 */
public interface UserDAO extends GenericDAO<User, Long> {

    /**
     * Returns user with given id. If id doesn't exist, returns null.
     *
     * @param id Id of required user
     * @return If exists, user with given id, else null.
     * @throws IllegalArgumentException Throws if given id is null.
     * @throws IllegalStateException Throws if factory is not initialized.
     */
    @Override
    User getById(Long id);

    /**
     * Saves given user into database
     *
     * @param user User to save
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws IllegalStateException Throws if factory is not initialized.
     */
    @Override
    void create(User user);

    /**
     * If exists given user in database, this method removes him.
     *
     * @param user User to remove
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws IllegalStateException Throws if factory is not initialized.
     */
    @Override
    void remove(User user);

    /**
     * Updates or creates given user, depending on whether user already exists.
     *
     * @param user User to update.
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws IllegalStateException Throws if factory is not initialized.
     */
    @Override
    void update(User user);

    /**
     * Returns all users in database.
     *
     * @return All users.
     * @throws IllegalStateException Throws if factory is not initialized.
     */
    @Override
    List<User> findAll();

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
