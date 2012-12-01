package cz.muni.fi.pa165.vozovna.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.TestSetup;
import cz.muni.fi.pa165.vozovna.dao.DriveDAOTest;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("DriveDAOTest-context.xml")
public class DriveDAOHibernateImplTest extends DriveDAOTest {

	@Override
	@Autowired
	public void setTestSetup(TestSetup testSetup) {
		super.setTestSetup(testSetup);
	}
	
	@Override
	@Autowired
    public void setDriveDao(DriveDAO driveDao) {
        super.setDriveDao(driveDao);
    }
}
