package cz.muni.fi.pa165.vozovna.ws;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.service.UserService;

import javax.jws.WebMethod;
import javax.jws.WebService;

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
	public List<UserDTO> findByLastName(String lastName) {
		return userServiceDelegate.findByLastName(lastName);
	}

	@Override
	@WebMethod
	public void generateTestDataIfNoneExist() {
		throw new UnsupportedOperationException();
		
	}

	@Override
	@WebMethod
	public UserDTO getByUsername(String username) {
		return userServiceDelegate.getByUsername(username);
	}

	@Override
	@WebMethod(exclude=true)
	public List<UserDTO> findByCriteria(List<Criterion> criterion,
			List<Order> orders) {
		throw new UnsupportedOperationException();
	}
}
