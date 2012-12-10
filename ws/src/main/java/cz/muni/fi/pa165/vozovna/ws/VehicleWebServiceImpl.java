package cz.muni.fi.pa165.vozovna.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;

@WebService
public class VehicleWebServiceImpl implements VehicleWebService {

	@Autowired
	VehicleService vehicleServiceDelegate;
	
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
	@WebMethod
	public List<VehicleDTO> findByUserClass(UserClassEnum userClass) {
		return vehicleServiceDelegate.findByUserClass(userClass);
	}
	
	@Override
	@WebMethod(operationName="getAvailableVehiclesForUser",action="EFGH")
	public List<VehicleDTO> getAvailableVehicles(User user, DateTime startDate,
			DateTime endDate) {
		
		return vehicleServiceDelegate.getAvailableVehicles(user, startDate, endDate);
	}

	@Override
	@WebMethod(operationName="getAvailableVehiclesForUserClass",action="ABCD")
	public List<VehicleDTO> getAvailableVehicles(UserClassEnum userClass,
			DateTime startDate, DateTime endDate) {
		return vehicleServiceDelegate.getAvailableVehicles(userClass, startDate, endDate);
	}

	@Override
	@WebMethod(exclude=true)
	public List<VehicleDTO> findByCriteria(List<Criterion> criterion,
			List<Order> orders) {
		throw new UnsupportedOperationException();
	}

}
