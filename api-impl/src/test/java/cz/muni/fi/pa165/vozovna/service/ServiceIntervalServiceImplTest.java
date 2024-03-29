package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("ServiceIntervalServiceTest-context.xml")
public class ServiceIntervalServiceImplTest extends ServiceIntervalServiceTest {

	@Override
	@Autowired
	public void setServiceIntervalService(ServiceIntervalService serviceIntervalService) {
		super.setServiceIntervalService(serviceIntervalService);
	}

	@Override
	@Autowired
	public void setServiceIntervalDao(ServiceIntervalDAO serviceIntervalDao) {
		super.setServiceIntervalDao(serviceIntervalDao);
	}

	@Override
	@Autowired
	public void setVehicleDAO(VehicleDAO vehicleDao) {
		super.setVehicleDAO(vehicleDao);
	}

}
