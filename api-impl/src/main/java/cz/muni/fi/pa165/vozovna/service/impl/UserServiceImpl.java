package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
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
            throw new IllegalArgumentException("null id");
        }
        User user = userDAO.getById(id);
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public Long create(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException("user has set id");
        }
        User entity = user.toNewUser();
        entity.setPassword(passwordEncoder.encodePassword(user.getPassword(), ""));
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
        if (user.getId() == null) {
            throw new IllegalArgumentException("user unexists");
        }
        userDAO.remove(userDAO.getById(user.getId()));
        user.setId(null);
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("user has no id set");
        }
        User entity = userDAO.getById(user.getId());
        if (entity == null) {
            throw new IllegalArgumentException("user unexists");
        }

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setUserClass(user.getUserClass());
        entity.setIsAdmin(user.getIsAdmin());
        entity.setUsername(user.getUsername());

        if (entity.getPassword() != null && !entity.getPassword().equals(user.getPassword())) {
            // password changed
            entity.setPassword(passwordEncoder.encodePassword(user.getPassword(), ""));
        }

        entity.setEnabled(user.getEnabled());

        userDAO.update(entity);
        user.fromUser(entity);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        // find
        List<User> users = userDAO.findAll();

        // transform results
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(new UserDTO(user));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("null lastName");
        }

        // find
        List<User> users = userDAO.findByLastName(lastName);

        return convertListOfUsersToListOfUserDTOs(users);
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

    @Override
    @Transactional(readOnly = true)
    public UserDTO getByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("null id");
        }

        User user = userDAO.getByUsername(username);

        if (user == null) {
            return null;
        }

        return new UserDTO(user);
    }

    /**
     * Converts list of vehicles to list of vehicle DTOs
     *
     * @param list List of vehicles
     * @return List of Drive Data Transform Objects
     */
    private static List<UserDTO> convertListOfUsersToListOfUserDTOs(List<User> entities) {
        List<UserDTO> result = new ArrayList<>();

        for (User item : entities) {
            result.add(new UserDTO(item));
        }

        return result;
    }
}
