package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("VehicleServiceTest-context.xml")
public class VehicleServiceImplTest extends VehicleServiceTest {

	@Autowired
    @Override
	public void setVehicleService(VehicleService vehicleService) {
		super.setVehicleService(vehicleService);
	}
	
	@Autowired
    @Override
	public void setVehicleDao(VehicleDAO vehicleDao) {
		super.setVehicleDao(vehicleDao);
	}
}
