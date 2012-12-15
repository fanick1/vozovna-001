package cz.muni.fi.pa165.vozovna.dao;
import cz.muni.fi.pa165.vozovna.TestSetup;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("ServiceIntervalDAOTest-context.xml")
public class ServiceIntervalDAOImplTest extends ServiceIntervalDAOTest {

	@Override
	@Autowired
	public void setTestSetup(TestSetup testSetup) {
		super.setTestSetup(testSetup);
	}
	
	@Override
	@Autowired
    public void setServiceIntervalDAO(ServiceIntervalDAO serviceIntervalDAO) {
		super.setServiceIntervalDAO(serviceIntervalDAO);
	}

	@Override
	@Autowired
    public void setVehicleDao(VehicleDAO vehicleDao) {
		super.setVehicleDao(vehicleDao);
    }
    
}
