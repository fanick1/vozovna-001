package cz.muni.fi.pa165.vozovna.ws;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

@WebService
public class UserWebServiceImpl implements UserWebService{

	@Autowired
	private UserService userServiceDelegate;
	
	@Override
	@WebMethod
	public UserDTO getById(Long id) {
		return userServiceDelegate.getById(id);
	}

	@Override
	@WebMethod
	public Long create(UserDTO user) {
		return userServiceDelegate.create(user);
	}

	@Override
	@WebMethod
	public void remove(UserDTO user) {
		userServiceDelegate.remove(user);
	}

	@Override
	@WebMethod
	public UserDTO update(UserDTO user) {
		return userServiceDelegate.update(user);
	}

	@Override
	@WebMethod
	public List<UserDTO> findAll() {
		return userServiceDelegate.findAll();
	}

	@Override
	@WebMethod
	public UserDTO getByUsername(String username) {
		return userServiceDelegate.getByUsername(username);
	}

}
