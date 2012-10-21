package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.dao.hibernate.UserDAOHibernateImpl;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Persistence;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public class UserDAOTest {

    private static final String PERSISTENCE_UNIT_NAME = "TestingPU";

    public UserDAOTest() {
    }

    private UserDAO createUserDAOFactory() {
        UserDAOHibernateImpl userDAO = new UserDAOHibernateImpl();
        userDAO.setEntityManagerFactory(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
        return userDAO;
    }

    private UserDAO createUserDAOFactoryWithoutEMF() {
        return new UserDAOHibernateImpl();
    }

    @Test
    public void testGetUserByIdWithoutEMF() {
        // UserDAO without Entity Manager Factory
        UserDAO dao = createUserDAOFactoryWithoutEMF();

        try {
            dao.create(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }

    }

    @Test
    public void testGetUserByIdWithNullArgument() {
        // UserDAO without Entity Manager Factory
        UserDAO dao = createUserDAOFactory();

        try {
            dao.getById(null);
            fail("Exception for null argument was not threw.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testValidCreateAndGetUserById() {
        UserDAO dao = createUserDAOFactory();
        User user = newDefaultUser();
        User result = null;
        try {
            dao.create(user);
            result = dao.getById(user.getId());
        } catch (Exception e) {
            fail("Unexpected exception was threw: " + e + " " + e.getMessage());
        }
        assertEquals(user, result);
        assertDeepEquals(user, result);
    }

    // CREATE
    @Test
    public void testCreateWithoutEMF() {
        UserDAO dao = createUserDAOFactoryWithoutEMF();

        try {
            dao.create(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testCreateWithNullArguments() {
        UserDAO dao = createUserDAOFactory();
        try {
            dao.create(null);
            fail("Exception for null argument was not threw.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

//    @Test
//    public void testCreateWithWrongUser() {     
//        UserDAO dao = createUserDAOFactory();
//        try {
//            dao.create(newDefaultUser());
//            fail("Exception for null argument was not threw.");
//        } catch(IllegalArgumentException e) {
//        } catch(Exception e) {
//            fail("Wrong Exception was threw: " + e + " " + e.getMessage() );
//        }
//    }
    @Test
    public void testUpdateWithoutEMF() {
        // UserDAO without Entity Manager Factory
        UserDAO dao = createUserDAOFactoryWithoutEMF();

        try {
            dao.update(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }

    }

    @Test
    public void testUpdateWithNullArgument() {
        // UserDAO without Entity Manager Factory
        UserDAO dao = createUserDAOFactoryWithoutEMF();

        try {
            dao.update(null);
            fail("Exception for null argument was not threw.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }

    }

    @Test
    public void testValidUpdate() {
        // UserDAO without Entity Manager Factory
        UserDAO dao = createUserDAOFactory();
        User user = newUser("Petr Novak", UserClassEnum.EMPLOYEE, true);

        try {
            dao.create(user);
            user.setLastName("Jan Novy");
            dao.update(user);
            assertNotSame("Name hasn't been updated.", "Petr Novak", user.getLastName());
        } catch (Exception e) {
            fail("Unexcepted exception was threw: " + e + " " + e.getMessage());
        }

    }

    // REMOVE
    @Test
    public void testRemoveWithoutEMF() {

        UserDAO dao = createUserDAOFactoryWithoutEMF();

        try {

            dao.remove(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testRemoveWithNullArgument() {
        // Remove user that is null 
        UserDAO dao = createUserDAOFactory();
        try {
            dao.remove(null);
            fail("Exception for null argument was not threw.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testValidRemove() {
        UserDAO dao = createUserDAOFactory();
        User user = newDefaultUser();
        User result = null;
        try {
            dao.create(user);
            Long id = user.getId();
            dao.remove(user);
            result = dao.getById(id);

        } catch (Exception e) {
            fail("Unexpected exception was threw: " + e + " " + e.getMessage());
        }
        assertNull("User was not removed. ", result);

    }

    // TEST FIND ALL
    @Test
    public void testFindAllWithoutEMF() {
        UserDAO dao = createUserDAOFactoryWithoutEMF();

        try {
            dao.findAll();
            fail("Exception for null argument was not threw.");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testValidFindAll() {
        UserDAO dao = createUserDAOFactory();
        User user1 = newUser("Jan Novak", UserClassEnum.EMPLOYEE, false);
        User user2 = newUser("Petr Novak", UserClassEnum.EMPLOYEE, false);
        List<User> results = null;
        try {
            dao.create(user1);
            dao.create(user2);

            results = dao.findAll();
        } catch (Exception e) {
            fail("Unexpected exception was threw: " + e + " " + e.getMessage());
        }
        assertEquals("Unexpected number of results.", 2, results.size());

        List<User> expected = Arrays.asList(user1, user2);

        Collections.sort(results, idComparator);
        Collections.sort(expected, idComparator);

        assertEquals(expected, results);
        assertDeepEquals(expected, results);
    }

    // FIND BY NAME
    @Test
    public void testFindByNameWithoutEMF() {
        UserDAO dao = createUserDAOFactoryWithoutEMF();

        try {
            dao.findByLastName("Novak");
            fail("Exception for null argument was not threw.");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testFindByNameWithWrongArgument() {
        UserDAO dao = createUserDAOFactory();

        try {
            dao.findByLastName(null);
            fail("Exception for null argument was not threw.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }

        try {
            dao.findByLastName("");
            fail("Exception for empty string as argument was not threw.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }

    @Test
    public void testValidFindByName() {
        UserDAO dao = createUserDAOFactory();
        User user = newUser("Jan Novak", UserClassEnum.EMPLOYEE, false);
        User user2 = newUser("Petr Novak", UserClassEnum.EMPLOYEE, false);
        List<User> all = null;
        List<User> results = null;
        try {
            dao.create(user);
            all = dao.findAll();
            dao.create(user2);

            results = dao.findByLastName(user.getLastName());
        } catch (Exception e) {
            fail("Unexpected exception was threw: " + e + " " + e.getMessage());
        }
        assertEquals("Unexpected number of results.", 1, results.size());

        assertEquals(user, results.get(0));
        assertDeepEquals(user, results.get(0));
        assertDeepEquals(all, results);

    }

    // Helping methods
    private void assertDeepEquals(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getIsAdmin(), actual.getIsAdmin());
        assertEquals(expected.getUserClass(), actual.getUserClass());
    }

    private void assertDeepEquals(List<User> expectedList, List<User> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            User expected = expectedList.get(i);
            User actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }

    // Factories
    private User newDefaultUser() {
        return newUser("Jan Novak", UserClassEnum.EMPLOYEE, false);
    }

    private User newUser(String name, UserClassEnum userClass, Boolean isAdmin) {
        User user = new User();
        user.setLastName(name);
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
