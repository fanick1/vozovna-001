/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entities.User;
import java.util.List;

/**
 *
 * @author eva.neduchalova
 */
public interface UserDao {
    
	/**
	 * Returns user with given id. If id does no exists returns null.
	 * @param id Id of required user
	 * @return If exists, user with given id, else null.
	 * @throws IllegalArgumentException Throws if given id is null.
	 * @throws IllegalStateException Throws if factory is not initialized.
	 */
    User getById(Long id);
    
	/**
	 * Saves given user to database
	 * @param user User to save
	 * @throws IllegalArgumentException Throws if given user is null.
	 * @throws IllegalStateException Throws if factory is not initialized.
	 */
    void create(User user);
    
	/**
	 * If exists given uset in database, removes it.
	 * @param user User to remove
	 * @throws IllegalArgumentException Throws if given user is null.
	 * @throws IllegalStateException Throws if factory is not initialized.
	 */
    void remove(User user);
    
	/**
	 * If exists, updates user or creates, if does not exists.
	 * @param user User to update.
	 * @throws IllegalArgumentException Throws if given user is null.
	 * @throws IllegalStateException Throws if factory is not initialized.
	 */
    void update(User user);
    
	/**
	 * Returns all users in database.
	 * @return All users.
	 * @throws IllegalStateException Throws if factory is not initialized.
	 */
    List<User> findAll();
    
	/**
	 * Returns all users with given name.
	 * @param name Name of required user
	 * @return List of ussers, with given name
	 * @throws IllegalArgumentException Throws if given user is null.
	 * @throws IllegalStateException Throws if factory is not initialized.
	 */
    List<User> findByName(String name);
}
