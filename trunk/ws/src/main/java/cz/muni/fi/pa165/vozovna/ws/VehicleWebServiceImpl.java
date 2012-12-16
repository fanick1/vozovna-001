package cz.muni.fi.pa165.vozovna.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

@WebService
public class VehicleWebServiceImpl implements VehicleWebService {

	@Autowired
	private	VehicleService vehicleServiceDelegate;
	
	@Override
	@WebMethod
	public VehicleDTO getById(Long id) {
		return vehicleServiceDelegate.getById(id);
	}

	@Override
	@WebMethod
	public Long create(VehicleDTO vehicle) {
		return vehicleServiceDelegate.create(vehicle);
	}

	@Override
	@WebMethod
	public void remove(VehicleDTO vehicle) {
		vehicleServiceDelegate.remove(vehicle);

	}

	@Override
	@WebMethod
	public VehicleDTO update(VehicleDTO vehicle) {
		return vehicleServiceDelegate.update(vehicle);
	}

	@Override
	@WebMethod
	public List<VehicleDTO> findAll() {
		return vehicleServiceDelegate.findAll();
	}
	
	@Override
	@WebMethod(operationName="getAvailableVehiclesForUser")
	public List<VehicleDTO> getAvailableVehicles(User user, DateTime startDate,
			DateTime endDate) {
		
		return vehicleServiceDelegate.getAvailableVehicles(user, startDate, endDate);
	}

	@Override
	@WebMethod(operationName="getAvailableVehiclesForUserClass")
	public List<VehicleDTO> getAvailableVehicles(UserClassEnum userClass,
			DateTime startDate, DateTime endDate) {
		return vehicleServiceDelegate.getAvailableVehicles(userClass, startDate, endDate);
	}

	public void setVehicleServiceDelegate(VehicleService vehicleServiceDelegate) {
		this.vehicleServiceDelegate = vehicleServiceDelegate;
	}

}
