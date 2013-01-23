package cz.muni.fi.pa165.vozovna.ws;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.joda.time.DateTime;

@WebService
public interface VehicleWebService extends VehicleService{
	@Override
	@WebMethod
	public VehicleDTO getById(Long id);

	@Override
	@WebMethod
	public Long create(VehicleDTO vehicle) ;

	@Override
	@WebMethod
	public void remove(VehicleDTO vehicle) ;

	@Override
	@WebMethod
	public VehicleDTO update(VehicleDTO vehicle);

	@Override
	@WebMethod
	public List<VehicleDTO> findAll();
	
	@Override
	@WebMethod(operationName="getAvailableVehiclesForUser")
	public List<VehicleDTO> getAvailableVehicles(UserDTO user, DateTime startDate,
			DateTime endDate);

	@Override
	@WebMethod(operationName="getAvailableVehiclesForUserClass")
	public List<VehicleDTO> getAvailableVehicles(UserClassEnum userClass,
			DateTime startDate, DateTime endDate);
}
