package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.AbstractGenericApiTest;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.Comparator;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public abstract class UserDAOTest extends AbstractGenericApiTest{

    private static final Logger logger = LoggerFactory.getLogger(UserDAOTest.class);
    
    public static final Long TEST_USER_ID = new Long(-1l);
    
    private UserDAO userDao;

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
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
        user.setPassword("my secret pass");
        user.setUsername("petnov");
        user.setEnabled(true);

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
        User user = newUser("Jan", "Novak", UserClassEnum.EMPLOYEE, false);
        user.setPassword("my secret pass");
        user.setUsername("jannov");
        user.setEnabled(true);
        
        return user;
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
