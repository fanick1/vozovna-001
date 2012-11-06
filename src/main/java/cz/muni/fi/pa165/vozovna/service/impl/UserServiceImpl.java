package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation User's Service Layer 
 * 
 * @author Lukas Hajek (359617@mail.muni.cz)
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(Long id) {
        if (id == null) {
            return null;
        }
        
        // get user
        User user = userDAO.getById(id);
 
        return  new UserDTO(user);
    }

    @Override
    @Transactional
    public Long create(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        
        User entity = user.toUser();
        
        userDAO.create(entity);
        
        
        user.fromUser(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        
        userDAO.remove(user.toUser());
        user.setId(null);
        
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        User entity = user.toUser();
        
        userDAO.update(entity);
       
        user.fromUser(entity);
        
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        // find
        List<User> users = userDAO.findAll();
                
        //  transform results
        List<UserDTO> result = new ArrayList<>();
        for(User user:users) {
           result.add(new UserDTO(user));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findByLastName(String lastName) {
        if(lastName == null) {
            throw new IllegalArgumentException("null lastName");
        }
        
        // find
        List<User> users = userDAO.findByLastName(lastName);
                
        // transform results
        List<UserDTO> result = new ArrayList<>();
        for(User user:users) {
            result.add(new UserDTO(user));
        }
        
        return result;
    }
  
    
}
