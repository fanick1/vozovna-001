package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("DriveServiceTest-context.xml")
public class DriveServiceImplTest extends DriveServiceTest {

	
	@Autowired
    @Override
	public void setDriveDAO(DriveDAO driveDAO) {
		super.setDriveDAO(driveDAO);
	}

	@Autowired
    @Override
	public void setUserDAO(UserDAO userDAO) {
		super.setUserDAO(userDAO);
	}
	
	@Autowired
    @Override
	public void setDriveService(DriveService driveService) {
		super.setDriveService(driveService);
	}
}
