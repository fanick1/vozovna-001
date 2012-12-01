package cz.muni.fi.pa165.vozovna.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.TestSetup;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("VehicleDAOTest-context.xml")
public class VehicleDAOHibernateImplTest extends VehicleDAOTest {

	@Override
	@Autowired
	public void setTestSetup(TestSetup testSetup) {
		super.setTestSetup(testSetup);
	}

	@Override
	@Autowired
    public void setVehicleDao(VehicleDAO vehicleDao) {
        super.setVehicleDao(vehicleDao);
    }
}
