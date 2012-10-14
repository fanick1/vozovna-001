/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.dao.jpa.ServiceIntervalDaoImpl;
import cz.muni.fi.pa165.vozovna.entities.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
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

/**
 *
 * @author jostri
 */
public class ServiceIntervalDAOTest {
    
    private ServiceIntervalDAO serviceIntervalDAO;
    private static final String PERSISTENCE_UNIT_NAME = "VozovnaPU";
    
    public ServiceIntervalDAOTest() {
        ServiceIntervalDaoImpl serviceDaoImplementation = new ServiceIntervalDaoImpl();
        serviceDaoImplementation.setFactory(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
        this.serviceIntervalDAO = serviceDaoImplementation;
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
	
	private static ServiceIntervalDAO getUnitializedServiceIntervalDAO() {
		return new ServiceIntervalDaoImpl();
	}
    
    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        
        Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
        Vehicle mercedes = ServiceIntervalDAOTest.getVehicle("Mercedes", 20000, "R4 Diesel", "E", UserClassEnum.PRESIDENT, "2a-447i-882a45", 2009);
        
        List<Date> dates = new ArrayList<Date>();
        dates.add(sdf.parse("22.7.2008"));
        dates.add(sdf.parse("4.11.2009"));
        
        ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(dates, "Prehodeni koles", 7, toyota);
        
        dates = new ArrayList<Date>();
        dates.add(sdf.parse("14.1.2010"));
        dates.add(sdf.parse("13.10.2010"));
        dates.add(sdf.parse("12.7.2011"));
        dates.add(sdf.parse("11.4.2012"));
        ServiceInterval interval2 = ServiceIntervalDAOTest.getServiceInterval(dates, "Kontrola stavu oleja", 10, mercedes);
        
        EntityManager localManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        localManager.getTransaction().begin();
        
        localManager.persist(toyota);
        localManager.persist(mercedes);
        localManager.persist(interval1);
        localManager.persist(interval2);
        
        localManager.getTransaction().commit();
        localManager.close();
    }
    
    @After
    public void tearDown() {
        EntityManager localManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
        
        // Remove intervals
        localManager.getTransaction().begin();
        TypedQuery<ServiceInterval> intervals = localManager.createQuery("FROM ServiceInterval e", ServiceInterval.class);
        for(ServiceInterval si: intervals.getResultList()) {
            localManager.remove(si);
        }
        
        localManager.getTransaction().commit();
        
        // Remove vehicles
        localManager.getTransaction().begin();
        TypedQuery<Vehicle> vehicles = localManager.createQuery("FROM Vehicle e", Vehicle.class);
        for(Vehicle v: vehicles.getResultList()) {
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
    public void testValidGetById() {
        try {
            ServiceInterval loaded = this.serviceIntervalDAO.getById(new Long("1"));
            
            if(loaded == null) {
                Assert.fail("Existing entity was not loaded!");
            }
        } catch(Exception ex) {
            Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
        }
    }
    
	@Test
	public void testValidCreate() {
		try {
			Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
			ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, toyota);
			
			this.serviceIntervalDAO.create(interval1);
			
			ServiceInterval loaded = this.serviceIntervalDAO.getById(interval1.getId());
			Assert.assertEquals("Inserted entity is not same as laoded", loaded, interval1);
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
	}
	
	@Test
	public void testValidUpdate() {
		try {
			Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
			ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, toyota);
			
			this.serviceIntervalDAO.create(interval1);
			
			interval1.setDescription("Vymeneni oleje");
			this.serviceIntervalDAO.update(interval1);
			
			ServiceInterval loaded = this.serviceIntervalDAO.getById(interval1.getId());
			Assert.assertNotSame("Description has not been updated.", loaded.getDescription(), loaded.getDescription());
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
	}
	
	@Test
	public void testValidFindAll() {
		try {
			List<ServiceInterval> intervals = this.serviceIntervalDAO.findAll();
			if(intervals.isEmpty()) {
				Assert.fail("Any entities were loaded from databse!");
			}
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
	}
	
	@Test
	public void testValidRemove() {
		try {
			List<ServiceInterval> intervals = this.serviceIntervalDAO.findAll();
			if(intervals.isEmpty()) {
				Assert.fail("Any entities were loaded from databse!");
			}
			
			ServiceInterval toRemove = intervals.get(0);
			this.serviceIntervalDAO.remove(toRemove);
			
			ServiceInterval loaded = this.serviceIntervalDAO.getById(toRemove.getId());
			Assert.assertNull("Service interval was not delete from databse", loaded);
		} catch(Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
	}
	
	@Test
	public void testValidFindByVehicle() {
		try {
			List<ServiceInterval> intervals = this.serviceIntervalDAO.findAll();
			if(intervals.isEmpty()) {
				Assert.fail("Any entities were loaded from databse!");
			}
			
			List<ServiceInterval> intervalsOfVehicle = this.serviceIntervalDAO.findAllByVehicle(intervals.get(0).getVehicle());
			if(intervalsOfVehicle.isEmpty()) {
				Assert.fail("Any intervals of given vehicle were loaded from databse!");
			}
		} catch (Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.getMessage());
		}
	}
	
	@Test
	public void testCreateFactoryNotInitialized() {
		try {
			Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
			ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, toyota);
			
			ServiceIntervalDAO manager = ServiceIntervalDAOTest.getUnitializedServiceIntervalDAO();
			manager.create(interval1);
			Assert.fail("Exception about uninitialized factory was not throwed.");
		} catch (IllegalStateException ex) {
			System.out.println("Ok");
		} catch (Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.toString());
		}
	}
	
	@Test
	public void testUpdateFactoryNotInitialized() {
		try {
			Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
			ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, toyota);
			
			ServiceIntervalDAO manager = ServiceIntervalDAOTest.getUnitializedServiceIntervalDAO();
			manager.update(interval1);
			Assert.fail("Exception about uninitialized factory was not throwed.");
		} catch (IllegalStateException ex) {
			System.out.println("Ok");
		} catch (Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.toString());
		}
	}
	
	@Test
	public void testRemoveFactoryNotInitialized() {
		try {
			Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
			ServiceInterval interval1 = ServiceIntervalDAOTest.getServiceInterval(new ArrayList<Date>(), "Prehodeni koles", 7, toyota);
			
			ServiceIntervalDAO manager = ServiceIntervalDAOTest.getUnitializedServiceIntervalDAO();
			manager.remove(interval1);
			Assert.fail("Exception about uninitialized factory was not throwed.");
		} catch (IllegalStateException ex) {
			System.out.println("Ok");
		} catch (Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.toString());
		}
	}
	
	@Test
	public void testGetByIdFactoryNotInitialized() {
		try {
			ServiceIntervalDAO manager = ServiceIntervalDAOTest.getUnitializedServiceIntervalDAO();
			manager.getById(new Long("1"));
			Assert.fail("Exception about uninitialized factory was not throwed.");
		} catch (IllegalStateException ex) {
			System.out.println("Ok");
		} catch (Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.toString());
		}
	}
	
	@Test
	public void testFindAllFactoryNotInitialized() {
		try {
			ServiceIntervalDAO manager = ServiceIntervalDAOTest.getUnitializedServiceIntervalDAO();
			manager.findAll();
			Assert.fail("Exception about uninitialized factory was not throwed.");
		} catch (IllegalStateException ex) {
			System.out.println("Ok");
		} catch (Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.toString());
		}
	}
	
	@Test
	public void testFindByVehicleFactoryNotInitialized() {
		try {
			Vehicle toyota = ServiceIntervalDAOTest.getVehicle("Toyota", 20000, "V-8", "Supra", UserClassEnum.MANAGER, "78-88as-ouu899", 2004);
			
			ServiceIntervalDAO manager = ServiceIntervalDAOTest.getUnitializedServiceIntervalDAO();
			manager.findAllByVehicle(toyota);
			Assert.fail("Exception about uninitialized factory was not throwed.");
		} catch (IllegalStateException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			Assert.fail("Unexpected exception was throwed: " + ex + " " + ex.toString());
		}
	}
}
