package cz.muni.fi.pa165.vozovna.service;

import java.util.List;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;

/**
 * User Service
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public interface UserService {

    /**
     * Returns User with given id. Returns null if user does not exists.
     * 
     * @param id ID of user
     * @return UserDTO if User with given id exists, null otherwise.
     * @throws IllegalArgumentException Throws if given id is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public UserDTO getById(Long id);

    /**
     * Saves given user into database.
     * 
     * @param user User to save
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public Long create(UserDTO user);

    /**
     * Removes given user from database.
     * 
     * @param user User to remove
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public void remove(UserDTO user);

    /**
     * Updates given user in database.
     * 
     * @param user User to update
     * @throws IllegalArgumentException Throws if given user is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public UserDTO update(UserDTO user);

    /**
     * Find and return all users in database.
     * 
     * @return List of all users.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public List<UserDTO> findAll();

    /**
     * Find and return users with given username.
     * 
     * @param username username.
     * @return userDTO of user with given username
     * @throws IllegalArgumentException Throws if given username is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public UserDTO getByUsername(String username);

}
