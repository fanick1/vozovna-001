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
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
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
        User user = userDAO.getById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.fromUser(user);
        return  userDTO;
    }

    @Override
    @Transactional
    public void create(UserDTO user) {
        userDAO.create(user.toUser());
    }

    @Override
    @Transactional
    public void remove(UserDTO user) {
        userDAO.remove(user.toUser());
    }

    @Override
    @Transactional
    public void update(UserDTO user) {
       userDAO.update(user.toUser());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = userDAO.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User u:users) {
            UserDTO dto = new UserDTO();
            dto.fromUser(u);
            userDTOs.add(dto);
        }
        return userDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findByLastName(String lastName) {
        List<User> users = userDAO.findByLastName(lastName);
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User u:users) {
            UserDTO dto = new UserDTO();
            dto.fromUser(u);
            userDTOs.add(dto);
        }
        return userDTOs;
    }

   
    
}
