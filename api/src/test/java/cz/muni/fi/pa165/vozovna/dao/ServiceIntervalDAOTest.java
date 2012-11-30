package cz.muni.fi.pa165.vozovna.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 *
 * @author Jozef Triscik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("ServiceIntervalDAOTest-context.xml")
public class ServiceIntervalDAOTest {

    private static final Logger logger = LoggerFactory.getLogger(ServiceIntervalDAOTest.class);

    @Autowired
    private ServiceIntervalDAO serviceIntervalDAO;

    public void setServiceIntervalDAO(ServiceIntervalDAO serviceIntervalDAO) {
        this.serviceIntervalDAO = serviceIntervalDAO;
    }

    @Autowired
    private VehicleDAO vehicleDao;

    public void setVehicleDao(VehicleDAO vehicleDao) {
        this.vehicleDao = vehicleDao;
    }
    
    @Before
    public void setUpTestData() throws Exception {
        ApplicationContext applicationContext = new org.springframework.context.support.ClassPathXmlApplicationContext("cz/muni/fi/pa165/vozovna/dao/DriveDAOTest-context.xml");
        DataSource ds = (DataSource) applicationContext.getBean("dataSource");
        Connection conn = ds.getConnection();
        try {
            IDatabaseConnection connection = new DatabaseConnection(conn);
            logger.info("*** Deletes data ***");
            DatabaseOperation.DELETE_ALL.execute(connection, new XmlDataSet(ClassLoader.getSystemResourceAsStream("TestDataSet.xml")));
            logger.info("*** Inserts new data ***");
            DatabaseOperation.CLEAN_INSERT.execute(connection, new XmlDataSet(ClassLoader.getSystemResourceAsStream("TestDataSet.xml")));
        } finally {
            DataSourceUtils.releaseConnection(conn, ds);
            logger.info("*** Finished inserting test data ***");
        }
    }

    private static Vehicle getVehicle(String brand, int distanceCount, String engineType,
            String type, UserClassEnum userClass, String vin, int year) {

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setDistanceCount(distanceCount);
        vehicle.setEngineType(engineType);
        vehicle.setType(type);
        vehicle.setUserClass(userClass);
        vehicle.setVin(vin);
        vehicle.setYearMade(year);

        return vehicle;
    }

    private static ServiceInterval getServiceInterval(List<Date> dates, String description, int inspectionInterval, Vehicle vehicle) {
        ServiceInterval interval = new ServiceInterval();
        interval.setDated(null);
        interval.setDescription(description);
        interval.setInspectionInterval(inspectionInterval);
        interval.setVehicle(vehicle);
        return interval;
    }


    @Test
    public void testCreateWithNullArgument() {
        try {
            serviceIntervalDAO.create(null);
            Assert.fail("Exception for null argument was not thrown!");
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void testUpdateWithNullArgument() {
        try {
            serviceIntervalDAO.update(null);
            Assert.fail("Exception for null argument was not thrown!");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testRemoveWithNullArgument() {
        try {
            serviceIntervalDAO.remove(null);
            Assert.fail("Exception for null argument was not thrown!");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testGetByIdWithNullArgument() {
        try {
            serviceIntervalDAO.getById(null);
            Assert.fail("Exception for null argument was not thrown!");
        } catch (IllegalArgumentException ex) {
            //OK
        } 
    }

    @Test
    public void testFindAllByVehicleWithNullArgument() {
        try {
            serviceIntervalDAO.findAllByVehicle(null);
            Assert.fail("Exception for null argument was not thrown!");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testValidCreateAndGetById() {
            Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
            vehicleDao.create(toyota);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, toyota);
            serviceIntervalDAO.create(interval1);

            System.out.println("Id to load: " + interval1.getId());
            ServiceInterval loaded = serviceIntervalDAO.getById(interval1.getId());
            Assert.assertEquals("Inserted entity is not same as loaded", interval1, loaded);
    }

    @Test
    public void testValidUpdate() {
            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
            vehicleDao.create(mercedes);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);

            serviceIntervalDAO.create(interval1);

            interval1.setDescription("Vymeneni oleje");
            serviceIntervalDAO.update(interval1);

            ServiceInterval loaded = serviceIntervalDAO.getById(interval1.getId());
            Assert.assertNotSame("Description has not been updated.", "Prehodeni koles", loaded.getDescription());
    }

    @Test
    public void testValidFindAll() {
            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "C", UserClassEnum.PRESIDENT, "2f-4a7i-121245", 2009);
            vehicleDao.create(mercedes);

            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);
            ServiceInterval interval2 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Vymena oleje", 7, mercedes);
            serviceIntervalDAO.create(interval1);
            serviceIntervalDAO.create(interval2);

            List<ServiceInterval> intervals = serviceIntervalDAO.findAll();
            if (intervals.isEmpty()) {
                Assert.fail("Any entities were loaded from databse!");
            }
    }

    @Test
    public void testValidRemove() {

            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 14000, "R4 Diesel", "A", UserClassEnum.PRESIDENT, "2f-4xxi-121245", 2009);
            vehicleDao.create(mercedes);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);
            serviceIntervalDAO.create(interval1);
            ServiceInterval loaded = serviceIntervalDAO.getById(interval1.getId());
            Assert.assertNotNull("Service interval was not created", loaded);
            serviceIntervalDAO.remove(interval1);

            loaded = serviceIntervalDAO.getById(interval1.getId());
            if (loaded != null) {
                Assert.fail("Interval was not deleted from database.");
            }
            Assert.assertNull("Service interval was not deleted from database", loaded);

    }

    @Test
    public void testValidFindByVehicle() {
            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 14000, "R4 Diesel", "A", UserClassEnum.PRESIDENT, "2f-4xxi-121245", 2009);
            vehicleDao.create(mercedes);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);
            serviceIntervalDAO.create(interval1);

            List<ServiceInterval> intervalsOfVehicle = serviceIntervalDAO.findAllByVehicle(mercedes);
            if (intervalsOfVehicle.isEmpty()) {
                Assert.fail("Any intervals of given vehicle were loaded from databse!");
            }
        
    }


}
