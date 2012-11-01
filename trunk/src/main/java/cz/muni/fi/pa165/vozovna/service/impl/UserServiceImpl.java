package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO getById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create(UserDTO user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(UserDTO user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(UserDTO user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserDTO> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserDTO> findByLastName(String lastName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    
}
