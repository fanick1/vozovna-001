package cz.muni.fi.pa165.vozovna.service.impl.ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.service.UserService;

@Stateless
@Local(value = UserService.class)
public class UserServiceEJBImpl implements UserService {

    //@Resource
    //private SessionContext ctx;

    @EJB
    private UserDAO userDao;

    @Override
    public UserDTO getById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Long create(UserDTO user) {
        User userEntity = user.toUser();
        userDao.create(userEntity);
        user.setId(userEntity.getId());
        return userEntity.getId();
    }

    @Override
    public void remove(UserDTO user) {
        User userEntity = user.toUser();
        userDao.remove(userEntity);
    }

    @Override
    public UserDTO update(UserDTO user) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<UserDTO> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<UserDTO> findByLastName(String lastName) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
