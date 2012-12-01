package cz.muni.fi.pa165.vozovna.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("UserServiceTest-context.xml")
public class UserServiceImplTest extends UserServiceTest {

	@Autowired
	public void setUserService(UserService userService) {
		super.setUserService(userService);
	}

	@Autowired
	public void setUserDao(UserDAO userDao) {
		super.setUserDao(userDao);
	}
}
