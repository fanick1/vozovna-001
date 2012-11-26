package cz.muni.fi.pa165.vozovna.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DriveServiceTest.class, ServiceIntervalServiceTest.class,
		UserServiceTest.class, VehicleServiceTest.class })
public class AllTests {

}
