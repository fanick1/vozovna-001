package cz.muni.fi.pa165.vozovna.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("DriveServiceTest-context.xml")
public class DriveServiceImplTest extends DriveServiceTest {

	
	@Autowired
	public void setDriveDAO(DriveDAO driveDAO) {
		super.setDriveDAO(driveDAO);
	}
	
	@Autowired
	public void setDriveService(DriveService driveService) {
		super.setDriveService(driveService);
	}
}
