package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation User's Service Layer 
 * 
 * @author Lukas Hajek (359617@mail.muni.cz)
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
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
  
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        if (username == null) {
            throw new UsernameNotFoundException("Invalid username (null)");
        }

        User user = userDAO.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username.");
        }

        return user;
    }

    /**
     * Uloží do DB dva uživatele: <br/>
     * user: admin, pass: admin, roles: ROLE_ADMIN <br/>
     * user: user, pass: user, roles: ROLE_USER
     */
    @Override
    @Transactional
    public void generateTestUsersIfNoneExist() {

        if (userDAO.findAll().isEmpty()) {

            User newAdmin = new User();
            newAdmin.setUsername("admin");
            newAdmin.setPassword(passwordEncoder.encodePassword("admin", null));
            newAdmin.setFirstName("Helmut");
            newAdmin.setLastName("Instalatér");
            newAdmin.setIsAdmin(Boolean.TRUE);
            newAdmin.setUserClass(UserClassEnum.PRESIDENT);
            userDAO.create(newAdmin);

            User newUser = new User();
            newUser.setUsername("user");
            newUser.setPassword(passwordEncoder.encodePassword("user", null));
            newUser.setFirstName("Ingrid");
            newUser.setLastName("Zrovna jsem se sprchovala");
            newUser.setIsAdmin(Boolean.FALSE);
            newUser.setUserClass(UserClassEnum.EMPLOYEE);
            userDAO.create(newUser);

        }
        
    }
    
}
