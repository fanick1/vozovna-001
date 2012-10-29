package cz.muni.fi.pa165.vozovna.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.dao.hibernate.ServiceIntervalDAOImpl;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServiceIntervalDAO serviceIntervalDAO;

    public void setServiceIntervalDAO(ServiceIntervalDAO serviceIntervalDAO) {
        this.serviceIntervalDAO = serviceIntervalDAO;
    }



    private VehicleDAO vehicleDao;
    private static final String PERSISTENCE_UNIT_NAME = "TestingPU";



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

    private static ServiceIntervalDAO getUnitializedServiceIntervalDAO() {
        return new ServiceIntervalDAOImpl();
    }

    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
        Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);

        List<Date> dates = new ArrayList<Date>();
//        dates.add(sdf.parse("22.7.2008"));
//        dates.add(sdf.parse("4.11.2009"));

        ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(dates, "Prehodeni koles", 7, toyota);

        dates = new ArrayList<Date>();
//        dates.add(sdf.parse("14.1.2010"));
//        dates.add(sdf.parse("13.10.2010"));
//        dates.add(sdf.parse("12.7.2011"));
//        dates.add(sdf.parse("11.4.2012"));
        ServiceInterval interval2 = ServiceIntervalDAOTest.getServiceInterval(dates, "Kontrola stavu oleja", 10, mercedes);

        EntityManager localManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        localManager.getTransaction().begin();

        localManager.persist(toyota);
//        localManager.persist(mercedes);
//        localManager.persist(interval1);
//        localManager.persist(interval2);

        localManager.getTransaction().commit();
        localManager.close();
    }

    @After
    public void tearDown() {
        EntityManager localManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();

        // Remove intervals
        localManager.getTransaction().begin();
        TypedQuery<ServiceInterval> intervals = localManager.createQuery("FROM ServiceInterval e", ServiceInterval.class);
        for (ServiceInterval si : intervals.getResultList()) {
            localManager.remove(si);
        }

        localManager.getTransaction().commit();

        // Remove vehicles
        localManager.getTransaction().begin();
        TypedQuery<Vehicle> vehicles = localManager.createQuery("FROM Vehicle e", Vehicle.class);
        for (Vehicle v : vehicles.getResultList()) {
            localManager.remove(v);
        }

        localManager.getTransaction().commit();

        localManager.close();
    }

    @Test
    public void testCreateWithNullArgument() {
        try {
            this.serviceIntervalDAO.create(null);
            Assert.fail("Exception for null argument was not throwed!");
        } catch (IllegalArgumentException ex) {
        } catch (Exception ex) {
            Assert.fail("Unknown exception type was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testUpdateWithNullArgument() {
        try {
            this.serviceIntervalDAO.update(null);
            Assert.fail("Exception for null argument was not throwed!");
        } catch (IllegalArgumentException ex) {
        } catch (Exception ex) {
            Assert.fail("Unknown exception type was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testRemoveWithNullArgument() {
        try {
            this.serviceIntervalDAO.remove(null);
            Assert.fail("Exception for null argument was not throwed!");
        } catch (IllegalArgumentException ex) {
        } catch (Exception ex) {
            Assert.fail("Unknown exception type was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testGetByIdWithNullArgument() {
        try {
            this.serviceIntervalDAO.getById(null);
            Assert.fail("Exception for null argument was not throwed!");
        } catch (IllegalArgumentException ex) {
        } catch (Exception ex) {
            Assert.fail("Unknown exception type was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testFindAllByVehicleWithNullArgument() {
        try {
            this.serviceIntervalDAO.findAllByVehicle(null);
            Assert.fail("Exception for null argument was not throwed!");
        } catch (IllegalArgumentException ex) {
        } catch (Exception ex) {
            Assert.fail("Unknown exception type was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testValidCreateAndGetById() {
        try {
            Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
            this.vehicleDao.create(toyota);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, toyota);
            this.serviceIntervalDAO.create(interval1);

            System.out.println("Id to load: " + interval1.getId());
            ServiceInterval loaded = this.serviceIntervalDAO.getById(interval1.getId());
            Assert.assertEquals("Inserted entity is not same as loaded", interval1, loaded);
        } catch (Exception ex) {
            Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testValidUpdate() {
        try {
            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
            this.vehicleDao.create(mercedes);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);

            this.serviceIntervalDAO.create(interval1);

            interval1.setDescription("Vymeneni oleje");
            this.serviceIntervalDAO.update(interval1);

            ServiceInterval loaded = this.serviceIntervalDAO.getById(interval1.getId());
            Assert.assertNotSame("Description has not been updated.", "Prehodeni koles", loaded.getDescription());
        } catch (Exception ex) {
            Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testValidFindAll() {
        try {
            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "C", UserClassEnum.PRESIDENT, "2f-4a7i-121245", 2009);
            this.vehicleDao.create(mercedes);

            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);
            ServiceInterval interval2 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Vymena oleje", 7, mercedes);
            this.serviceIntervalDAO.create(interval1);
            this.serviceIntervalDAO.create(interval2);

            List<ServiceInterval> intervals = this.serviceIntervalDAO.findAll();
            if (intervals.isEmpty()) {
                Assert.fail("Any entities were loaded from databse!");
            }
        } catch (Exception ex) {
            Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testValidRemove() {
        try {
            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 14000, "R4 Diesel", "A", UserClassEnum.PRESIDENT, "2f-4xxi-121245", 2009);
            this.vehicleDao.create(mercedes);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);
            this.serviceIntervalDAO.create(interval1);
            ServiceInterval loaded = this.serviceIntervalDAO.getById(interval1.getId());
            Assert.assertNotNull("Service interval was not created", loaded);

            this.serviceIntervalDAO.remove(interval1);

            loaded = this.serviceIntervalDAO.getById(interval1.getId());
            if (loaded != null) {
                Assert.fail("Interval was not deleted from database.");
            }
            Assert.assertNull("Service interval was not deleted from database", loaded);
        } catch (Exception ex) {
            Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
        }
    }

    @Test
    public void testValidFindByVehicle() {
        try {
            Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 14000, "R4 Diesel", "A", UserClassEnum.PRESIDENT, "2f-4xxi-121245", 2009);
            this.vehicleDao.create(mercedes);
            ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, mercedes);
            this.serviceIntervalDAO.create(interval1);

            List<ServiceInterval> intervalsOfVehicle = this.serviceIntervalDAO.findAllByVehicle(mercedes);
            if (intervalsOfVehicle.isEmpty()) {
                Assert.fail("Any intervals of given vehicle were loaded from databse!");
            }
        } catch (Exception ex) {
            Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
        }
    }


}
