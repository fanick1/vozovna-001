package cz.muni.fi.pa165.vozovna.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
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
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * 
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("UserDAOTest-context.xml")
public class UserDAOTest {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    public static final Long TEST_USER_ID = new Long(-1l);

    @Autowired
    private UserDAO userDao;

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }
    
    @Before
    public void setUpTestData() throws Exception {
        ApplicationContext applicationContext = new org.springframework.context.support.ClassPathXmlApplicationContext("cz/muni/fi/pa165/vozovna/dao/DriveDAOTest-context.xml");
        DataSource ds = (DataSource) applicationContext.getBean("dataSource");
        Connection conn = ds.getConnection();
        try {
            IDatabaseConnection connection = new DatabaseConnection(conn);
            logger.info("*** Deletes data ***");
            DatabaseOperation.DELETE_ALL.execute(connection, new XmlDataSet(new FileInputStream("src/test/resources/TestDataSet.xml")));
            logger.info("*** Inserts new data ***");
            DatabaseOperation.CLEAN_INSERT.execute(connection, new XmlDataSet(new FileInputStream("src/test/resources/TestDataSet.xml")));
        } finally {
            DataSourceUtils.releaseConnection(conn, ds);
            logger.info("*** Finished inserting test data ***");
        }
    }


    @Test
    public void testGetUserByIdWithNullArgument() {
        try {
            userDao.getById(null);
            fail("Exception for null argument was not thrown.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    @Transactional
    public void testValidCreateAndGetUserById() {
        User user = newDefaultUser();
        User result = null;
        try {
            userDao.create(user);
            result = userDao.getById(user.getId());
        } catch (Exception e) {
            fail("Unexpected exception was thrown: " + e + " " + e.getMessage());
        }
        assertEquals(user, result);
        assertDeepEquals(user, result);
    }

    // CREATE

    @Test
    public void testCreateWithNullArguments() {
        try {
            userDao.create(null);
            fail("Exception for null argument was not thrown.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was thrown: " + e + " " + e.getMessage());
        }
    }

//    @Test
//    public void testCreateWithWrongUser() {     
//        UserDAO dao = createUserDAOFactory();
//        try {
//            dao.create(newDefaultUser());
//            fail("Exception for null argument was not thrown.");
//        } catch(IllegalArgumentException e) {
//        } catch(Exception e) {
//            fail("Wrong Exception was thrown: " + e + " " + e.getMessage() );
//        }
//    }
   


    @Test
    public void testValidUpdate() {
        // UserDAO without Entity Manager Factory
        User user = newUser("Petr", "Novak", UserClassEnum.EMPLOYEE, true);

        try {
            userDao.create(user);
            user.setLastName("Jan Novy");
            userDao.update(user);
            assertNotSame("Name hasn't been updated.", "Petr Novak", user.getLastName());
        } catch (Exception e) {
            fail("Unexcepted exception was thrown: " + e + " " + e.getMessage());
        }

    }

    // REMOVE
  

    @Test
    public void testRemoveWithNullArgument() {
        // Remove user that is null 
        try {
            userDao.remove(null);
            fail("Exception for null argument was not thrown.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was thrown: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testValidRemove() {
        User user = newDefaultUser();
        User result = null;
            userDao.create(user);
            Long id = user.getId();
            userDao.remove(user);
            result = userDao.getById(id);

        assertNull("User was not removed. ", result);

    }

    // TEST FIND ALL
   
    @Test
    public void testValidFindAll() {
        List<User> results = userDao.findAll();
        assertEquals("Unexpected number of results.", 1, results.size());

//        List<User> expected = Arrays.asList(user1, user2);
//
//        Collections.sort(results, idComparator);
//        Collections.sort(expected, idComparator);
//
//        assertEquals(expected, results);
//        assertDeepEquals(expected, results);
    }

    // FIND BY NAME
   
    @Test
    public void testFindByNameWithWrongArgument() {

        try {
            userDao.findByLastName(null);
            fail("Exception for null argument was not thrown.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was thrown: " + e + " " + e.getMessage());
        }

    }

    @Test
    public void testValidFindByName() {
        List<User> results = userDao.findByLastName("Rimmer");
        assertEquals("Unexpected number of results.", 1, results.size());
        assertEquals(TEST_USER_ID, results.get(0).getId());
    }

    // Helping methods
    private void assertDeepEquals(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getIsAdmin(), actual.getIsAdmin());
        assertEquals(expected.getUserClass(), actual.getUserClass());
    }


    // Factories
    private User newDefaultUser() {
        return newUser("Jan", "Novak", UserClassEnum.EMPLOYEE, false);
    }

    private User newUser(String firstName, String lastName, UserClassEnum userClass, Boolean isAdmin) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserClass(userClass);
        user.setIsAdmin(isAdmin);
        return user;

    }


    
    private static Comparator<User> idComparator = new Comparator<User>() {

        @Override
        public int compare(User o1, User o2) {
            return Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId()));
        }
    };
}
