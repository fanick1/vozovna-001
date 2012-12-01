package cz.muni.fi.pa165.vozovna.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("VehicleServiceTest-context.xml")
public class VehicleServiceImplTest extends VehicleServiceTest {

	@Autowired
	public void setVehicleService(VehicleService vehicleService) {
		super.setVehicleService(vehicleService);
	}
	
	@Autowired
	public void setVehicleDao(VehicleDAO vehicleDao) {
		super.setVehicleDao(vehicleDao);
	}
}
