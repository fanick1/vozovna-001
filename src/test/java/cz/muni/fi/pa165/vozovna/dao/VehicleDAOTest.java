/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.dao.hibernate.VehicleDAOHibernateImpl;
import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;
import javax.persistence.Persistence;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Banana_King
 */
public class VehicleDAOTest {
    
    private VehicleDAO vehicleDao;
    
    public VehicleDAOTest() {
		VehicleDAOHibernateImpl vehicleDaoImpl = new VehicleDAOHibernateImpl();
		vehicleDaoImpl.setEntityManagerFactory(Persistence.createEntityManagerFactory("VozovnaPU"));
		this.vehicleDao = vehicleDaoImpl;
    }

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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

    /**
     * Test of getById method, of class VehicleDAO.
     */
    @Test
    public void testCreateAndGetById() {
        try {
			Vehicle mercedes = VehicleDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
			this.vehicleDao.create(mercedes);
			
			Vehicle loaded = this.vehicleDao.getById(mercedes.getId());
			Assert.assertEquals("Insserted and loaded vehicles should be same.",mercedes, loaded);
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
    }

    /**
     * Test of remove method, of class VehicleDAO.
     */
    @Test
    public void testRemove() {
        try {
			Vehicle mercedes = VehicleDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
			this.vehicleDao.create(mercedes);
			Long id = mercedes.getId();
			System.out.println("Log: vehicle created!");
			
			this.vehicleDao.remove(mercedes);
			System.out.println("Log: vehicle removed!");
			
			Vehicle loaded = this.vehicleDao.getById(id);
			System.out.println("Log: vehicle loaded!");
			
			Assert.assertNull("Vehicle was not deleted from database", loaded);
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
    }

    /**
     * Test of update method, of class VehicleDAO.
     */
    @Test
    public void testUpdate() {
        try{
			Vehicle mercedes = VehicleDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
			this.vehicleDao.create(mercedes);
			Long id = mercedes.getId();
			
			mercedes.setType("C");
			this.vehicleDao.update(mercedes);
			
			Vehicle loaded = this.vehicleDao.getById(id);
			Assert.assertNotSame("Vehicle was not upadted!", "E", loaded.getType());
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
    }

    /**
     * Test of findAll method, of class VehicleDAO.
     */
    @Test
    public void testFindAll() {
        try {
			Vehicle mercedes1 = VehicleDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
			Vehicle mercedes2 = VehicleDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "C", UserClassEnum.PRESIDENT, "98-447i-883345", 2009);
			this.vehicleDao.create(mercedes1);
			this.vehicleDao.create(mercedes2);
			
			if(this.vehicleDao.findAll().isEmpty()) {
				Assert.fail("Any vehicles were loaded from database.");
			}
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
    }

    /**
     * Test of findByUserClass method, of class VehicleDAO.
     */
    @Test
    public void testFindByUserClass() {
        try {
			Vehicle mercedes1 = VehicleDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
			Vehicle mercedes2 = VehicleDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "C", UserClassEnum.MANAGER, "98-447i-883345", 2009);
			this.vehicleDao.create(mercedes1);
			this.vehicleDao.create(mercedes2);
			
			List<Vehicle> required = this.vehicleDao.findByUserClass(UserClassEnum.MANAGER);
			Assert.assertEquals("Vehicle with right user class was not loaded.",required.get(0), mercedes2);
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
    }

}
