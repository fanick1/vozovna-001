/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import java.util.List;
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
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getById method, of class VehicleDAO.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        Long id = null;
        VehicleDAO instance = new VehicleDAOImpl();
        List expResult = null;
        List result = instance.getById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class VehicleDAO.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Vehicle vehicle = null;
        VehicleDAO instance = new VehicleDAOImpl();
        instance.create(vehicle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class VehicleDAO.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Vehicle vehicle = null;
        VehicleDAO instance = new VehicleDAOImpl();
        instance.remove(vehicle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class VehicleDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Vehicle vehicle = null;
        VehicleDAO instance = new VehicleDAOImpl();
        instance.update(vehicle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class VehicleDAO.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        VehicleDAO instance = new VehicleDAOImpl();
        List expResult = null;
        List result = instance.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findByUserClass method, of class VehicleDAO.
     */
    @Test
    public void testFindByUserClass() {
        System.out.println("findByUserClass");
        int userClass = 0;
        VehicleDAO instance = new VehicleDAOImpl();
        List expResult = null;
        List result = instance.findByUserClass(userClass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class VehicleDAOImpl implements VehicleDAO {

        public List<Vehicle> getById(Long id) {
            return null;
        }

        public void create(Vehicle vehicle) {
        }

        public void remove(Vehicle vehicle) {
        }

        public void update(Vehicle vehicle) {
        }

        public List<Vehicle> findAll() {
            return null;
        }

        public List<Vehicle> findByUserClass(int userClass) {
            return null;
        }
    }
}
