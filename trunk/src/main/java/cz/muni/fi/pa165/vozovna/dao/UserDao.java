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
    
    User getById(Long id);
    
    void create(User user);
    
    void remove(User user);
    
    void update(User user);
    
    List<User> findAll();
    
    List<User> findByName(String name);
}
