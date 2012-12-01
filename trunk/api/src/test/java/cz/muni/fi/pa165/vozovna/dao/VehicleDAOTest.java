package cz.muni.fi.pa165.vozovna.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.pa165.vozovna.AbstractGenericApiTest;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * @author eva.neduchalova
 */
public abstract class VehicleDAOTest extends AbstractGenericApiTest{
    
    private static final Logger logger = LoggerFactory.getLogger(VehicleDAOTest.class);
    public static final Long TEST_VEHICLE_ID = new Long(-1l);

    private VehicleDAO vehicleDao;

    public void setVehicleDao(VehicleDAO vehicleDao) {
        this.vehicleDao = vehicleDao;
    }
    
    private static Vehicle createTestVehicle(String brand, int distanceCount, String engineType,
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

    /**
     * Test of getById method, of class VehicleDAO.
     */
    @Test
    public void testCreateAndGetById() {
            Vehicle mercedes = VehicleDAOTest.createTestVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
            this.vehicleDao.create(mercedes);
            Vehicle loaded = this.vehicleDao.getById(mercedes.getId());
            Assert.assertEquals("Inserted and loaded vehicles should be same.", mercedes, loaded);
    }

    /**
     * Test of remove method, of class VehicleDAO.
     */
    @Test
    public void testRemove() {
            Vehicle mercedes = VehicleDAOTest.createTestVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
            this.vehicleDao.create(mercedes);
            Long id = mercedes.getId();
            System.out.println("Log: vehicle [" + id + "] created!");

            this.vehicleDao.remove(mercedes);
            System.out.println("Log: vehicle [" + id + "] removed!");

            Vehicle loaded = this.vehicleDao.getById(id);
            System.out.println("Log: vehicle [" + id + "] loaded!");

            Assert.assertNull("Vehicle was not deleted from database", loaded);
    }

    /**
     * Test of update method, of class VehicleDAO.
     */
    @Test
    public void testUpdate() {
            Vehicle mercedes = VehicleDAOTest.createTestVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
            this.vehicleDao.create(mercedes);
            Long id = mercedes.getId();

            mercedes.setType("C");
            this.vehicleDao.update(mercedes);

            Vehicle loaded = this.vehicleDao.getById(id);
            Assert.assertNotSame("Vehicle was not upadted!", "E", loaded.getType());
    }

    /**
     * Test of findAll method, of class VehicleDAO.
     */
    @Test
    public void testFindAll() {
            Vehicle mercedes1 = VehicleDAOTest.createTestVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
            Vehicle mercedes2 = VehicleDAOTest.createTestVehicle("Mercedes", 20000, "R4 Diesel", "C", UserClassEnum.PRESIDENT, "98-447i-883345", 2009);
            this.vehicleDao.create(mercedes1);
            this.vehicleDao.create(mercedes2);

            if (this.vehicleDao.findAll().isEmpty()) {
                Assert.fail("Any vehicles were loaded from database.");
            }
    }

    /**
     * Test of findByUserClass method, of class VehicleDAO.
     */
    @Test
    public void testFindByUserClass() {
            List<Vehicle> required = this.vehicleDao.findByUserClass(UserClassEnum.MANAGER);
            Assert.assertEquals("Vehicle with right user class was not loaded.", required.get(0).getId(), TEST_VEHICLE_ID );
    }

    @Test
    public void testCreateNullArgument() {
        try {
            this.vehicleDao.create(null);
            Assert.fail("Exception for null argument wasn't thrown.");
        } catch (IllegalArgumentException ex) {
            // Ok
        }
    }

    @Test
    public void testRemoveNullArgument() {
        try {
            this.vehicleDao.remove(null);
            Assert.fail("Exception for null argument wasn't thrown.");
        } catch (IllegalArgumentException ex) {
            // Ok
        }
    }

    @Test
    public void testUpdateNullArgument() {
        try {
            this.vehicleDao.update(null);
            Assert.fail("Exception for null argument wasn't thrown.");
        } catch (IllegalArgumentException ex) {
            // Ok
        }
    }

    @Test
    public void testGetByIdNullArgument() {
        try {
            this.vehicleDao.getById(null);
            Assert.fail("Exception for null argument wasn't thrown.");
        } catch (IllegalArgumentException ex) {
            // Ok
        }
    }

}
