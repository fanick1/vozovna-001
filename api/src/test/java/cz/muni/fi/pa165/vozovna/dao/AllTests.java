package cz.muni.fi.pa165.vozovna.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DriveDAOTest.class, ServiceIntervalDAOTest.class,
		UserDAOTest.class, VehicleDAOTest.class })
public class AllTests {

}
