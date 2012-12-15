package cz.muni.fi.pa165.vozovna.ws;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.joda.time.DateTime;

@WebService
public interface VehicleWebService extends VehicleService{
	@Override
	@WebMethod(operationName="getAvailableVehiclesForUser",action="EFGH")
	public List<VehicleDTO> getAvailableVehicles(User user, DateTime startDate,
			DateTime endDate) ;
}
