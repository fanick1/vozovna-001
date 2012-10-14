/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entities.VozovnaUser;
import java.util.List;

/**
 *
 * @author eva.neduchalova
 */
public interface UserDao {
    
    VozovnaUser getById(Long id);
    
    void create(VozovnaUser user);
    
    void remove(VozovnaUser user);
    
    void update(VozovnaUser user);
    
    List<VozovnaUser> findAll();
    
    List<VozovnaUser> findByName(String name);
}
