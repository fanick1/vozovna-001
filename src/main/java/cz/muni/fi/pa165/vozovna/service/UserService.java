package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import java.util.List;

/**
 * User Service 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public interface UserService {
    /**
     * Returns User with given id. Returns null if user does not exists.
     * 
     * @param id        ID of user
     * @return          UserDTO if User with given id exists, null otherwise.
     * @throws IllegalArgumentException     Throws if given id is null.
     * @throws IllegalStateException        Throws if factory is not set.
     */
    public UserDTO getById(Long id);

    /**
     * Saves given user into database.
     * 
     * @param user      User to save
     * @throws IllegalArgumentException     Throws if given user is null.
     * @throws IllegalStateException        Throws if factory is not set.
     */
    public void create(UserDTO user);

    /**
     * Removes given user from database.
     * 
     * @param user      User to remove
     * @throws IllegalArgumentException     Throws if given user is null.
     * @throws IllegalStateException        Throws if factory is not set.
     */
    public void remove(UserDTO user);

    /**
     * Updates given user in database.
     * 
     * @param user      User to update
     * @throws IllegalArgumentException     Throws if given user is null.
     * @throws IllegalStateException        Throws if factory is not set.
     */
    public void update(UserDTO user);

    /**
     * Find and return all users in database.
     * 
     * @return List of all users.
     * @throws IllegalStateException        Throws if factory is not set.
     */
    public List<UserDTO> findAll();

    /**
     * Find and return users with given lastname.
     * 
     * @param lastName      Lastname.
     * @return List of users of given user.
     * @throws IllegalArgumentException     Throws if given user is null.
     * @throws IllegalStateException        Throws if factory is not set.
     */
    public List<UserDTO> findByLastName(String lastName);

}
