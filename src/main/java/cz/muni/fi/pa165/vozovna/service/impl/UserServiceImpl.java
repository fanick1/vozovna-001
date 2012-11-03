package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.service.UserService;
import cz.muni.fi.pa165.vozovna.service.exceptions.UserServiceFailureException;
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
        User user;
        try {
            user = userDAO.getById(id);
        } catch(Exception e) {
            throw new UserServiceFailureException("Finding user by ID was failded.", e);
        }

        return  new UserDTO(user);
    }

    @Override
    @Transactional
    public Long create(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        
        User entity = user.toUser();
        try {
            userDAO.create(entity);
        } catch(Exception e) {
            throw new UserServiceFailureException("User creation was failded.", e);
        }
        
        user.fromUser(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        try {
            userDAO.remove(user.toUser());
            user.setId(null);
        } catch(Exception e) {
            throw new UserServiceFailureException("User deletion was failed.", e);
        }
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        User entity = user.toUser();
        
        try {
            userDAO.update(entity);
        } catch(Exception e) {
            throw new UserServiceFailureException("User update was failed.", e);
        }
        user.fromUser(entity);
        
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        // find
        List<User> users;
        try {
             users = userDAO.findAll();
        } catch(Exception e) {
            throw new UserServiceFailureException("Finding all users was failed.", e);
        }
        
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
        List<User> users;
        try {
             users = userDAO.findByLastName(lastName);
        } catch(Exception e) {
            throw new UserServiceFailureException("Finding all users by last name was failed.", e);
        }
        
        // transform results
        List<UserDTO> result = new ArrayList<>();
        for(User user:users) {
            result.add(new UserDTO(user));
        }
        
        return result;
    }
  
    
}
