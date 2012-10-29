package cz.muni.fi.pa165.vozovna.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.entity.Drive;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("DriveDAOTest-context.xml")
public class DriveDAOTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DriveDAO driveDao;

    public void setDriveDao(DriveDAO driveDao) {
        this.driveDao = driveDao;
    }

    public static final Long TEST_VEHICLE_ID = new Long(-1l);
    
    @Before
    public void setUpTestData() throws Exception {
//        ApplicationContext applicationContext = new org.springframework.context.support.ClassPathXmlApplicationContext("cz/muni/fi/pa165/vozovna/dao/DriveDAOTest-context.xml");
//        DataSource ds = (DataSource) applicationContext.getBean("dataSource");
//        Connection conn = ds.getConnection();
//        try {
//            IDatabaseConnection connection = new DatabaseConnection(conn);
//            logger.info("*** Deletes data ***");
//            DatabaseOperation.DELETE_ALL.execute(connection, new XmlDataSet(new FileInputStream("src/test/resources/TestDataSet.xml")));
//            logger.info("*** Inserts new data ***");
//            DatabaseOperation.CLEAN_INSERT.execute(connection, new XmlDataSet(new FileInputStream("src/test/resources/TestDataSet.xml")));
//        } finally {
//            DataSourceUtils.releaseConnection(conn, ds);
//            logger.info("*** Finished inserting test data ***");
//        }
    }
    

    /**
     * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#getById(java.lang.Long)}.
     */
    @Test
    public void testGetById() {

        logger.debug("test get Drive by null id");
        try {
            driveDao.getById(null);
            fail("IllegalArgumentException expected when trying to get Drive by null id");
        } catch (IllegalArgumentException e) {
            // OK
        }

        logger.debug("test get Drive by id which unexists");
        assertNull("null expected", driveDao.getById(999l));

        logger.debug("test get Drive by id which exists");
        Drive result = driveDao.getById(TEST_VEHICLE_ID);
        assertNotNull("getById returned null instead of drive", result);
        assertEquals("result has wrong id", TEST_VEHICLE_ID, result.getId());

        // TODO assert equals na ostatni parametry - po doplneni testovacich dat

        // assertEquals(drive, test1);
        // assertEquals(franta, test1.getUser());
        // assertEquals(vehicle, test1.getVehicle());

    }

    /**
     * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#create(cz.muni.fi.pa165.vozovna.entities.Drive)}.
     */
    @Test
    @DirtiesContext
    public void testCreate() {

        logger.debug("test create with null argument");
        try {
            driveDao.create(null);
            fail("IllegalArgumentException expected when trying to create null Drive");
        } catch (IllegalArgumentException e) {
            // OK
        }

        logger.debug("test create normal");
        Drive newDrive = new Drive();
        driveDao.create(newDrive);
        assertNotNull(newDrive.getId());

        logger.debug("test create existing Drive");
        try {
            driveDao.create(newDrive); // duplicity
            fail("creating duplicit Drive shoudn`t be possible; exception expected");
        } catch (Exception e) {
            // OK
        }

    }

    /**
     * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#update(cz.muni.fi.pa165.vozovna.entities.Drive)}.
     */
    @Test
    public void testUpdate() {

        logger.debug("test update null drive");
        try {
            driveDao.getById(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // OK
        }
        

        logger.debug("test update drive normal");
        Drive driveToUpdate = driveDao.getById(TEST_VEHICLE_ID);
        Drive driveToUpdate2 = driveDao.getById(-1l);
        Drive driveToUpdate3 = driveDao.getById(new Long(-1l));
        
        DateTime oldDateTo = driveToUpdate.getDateTo();
        DateTime newDateTo = new DateTime();
        if (newDateTo.equals(oldDateTo)) {
            newDateTo = newDateTo.plusMinutes(1);
        }
        driveToUpdate.setDateTo(newDateTo);
        driveDao.update(driveToUpdate);
        Drive updatedDrive = driveDao.getById(TEST_VEHICLE_ID);
        assertEquals("vehicle hasn't been updated in database", driveToUpdate, updatedDrive);

    }

    /**
     * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#remove(cz.muni.fi.pa165.vozovna.entities.Drive)}.
     */
    @Test
    public void testRemove() {
        logger.debug("test remove null Drive");
        try {
            driveDao.remove(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // OK
        }

        logger.debug("test remove drive normal");
        Drive newDrive = new Drive();
        driveDao.create(newDrive);
        Long newDriveId = newDrive.getId();
        assertNotNull("Id of drive is not set. Error in create(Drive drive) method.", newDriveId);
        driveDao.remove(newDrive);
        assertNotNull("drive hasn`t been removed", driveDao.getById(newDriveId));
    }

    /**
     * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#findAll()}.
     */
    @Test
    public void testFindAll() {
        logger.debug("test findAll");
        List<Drive> list = driveDao.findAll();
        if (list == null) {
            fail("Non null list expected");
        }
        if (list.size() != 1) {
            fail("Expected list of size 1. Instead got " + list.size());
        }
    }

    /**
     * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#findByUser(cz.muni.fi.pa165.vozovna.entities.User)}.
     */
    @Test
    public void testFindByUser() {
        logger.debug("test findByUser");
        try {
            driveDao.findByUser(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // OK
        }

        // TODO až bude vyplněné create-schema.sql, tak sem přidat kontrolu hledání podle uživatele

    }

}
