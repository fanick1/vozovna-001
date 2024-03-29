package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.AbstractGenericApiTest;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import java.util.List;
import org.joda.time.DateTime;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;

/**
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
public abstract class DriveDAOTest extends AbstractGenericApiTest{

    private static final Logger logger = LoggerFactory.getLogger(DriveDAOTest.class);


    private DriveDAO driveDao;
    
    public void setDriveDao(DriveDAO driveDao) {
        this.driveDao = driveDao;
    }

    public static final Long TEST_VEHICLE_ID = new Long(-1l);

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

        assertEquals(new DateTime("2012-10-01").plusHours(2), result.getDateFrom());
        assertEquals(new DateTime("2012-10-07").plusHours(2), result.getDateTo());
        assertEquals(new Integer(60), result.getDistance());
        //TODO namapovat tohle enum na String
        //assertEquals(DriveStateEnum.RESERVED, result.getState());
        
        
        assertNotNull(result.getUser());
        assertNotNull(result.getVehicle());
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
//        try {
//            driveDao.create(newDrive); // duplicity
//            fail("creating duplicit Drive shoudn`t be possible; exception expected");
//        } catch (Exception e) {
//            // OK
//        }

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
        assertNull("drive hasn`t been removed", driveDao.getById(newDriveId));
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
