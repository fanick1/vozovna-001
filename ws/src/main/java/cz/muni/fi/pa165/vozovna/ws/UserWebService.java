package cz.muni.fi.pa165.vozovna.ws;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface UserWebService extends UserService {
	@Override
	@WebMethod
	public UserDTO getById(Long id);

	@Override
	@WebMethod
	public Long create(UserDTO user);

	@Override
	@WebMethod
	public void remove(UserDTO user);

	@Override
	@WebMethod
	public UserDTO update(UserDTO user);

	@Override
	@WebMethod
	public List<UserDTO> findAll();

	@Override
	@WebMethod
	public UserDTO getByUsername(String username);


}
