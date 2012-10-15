/**
 *  Copyright 2012 Frantisek Veverka, Eva Neduchalova, Jozef Triscik, Lukas Hajek
 *  The latest (and the greatest) version of this software can be obtained at 
 *	http://code.google.com/p/vozovna-001/
 *
 *  This file is part of Vozovna.
 *   Vozovna is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Vozovna is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Vozovna.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.dao.hibernate.ServiceIntervalDaoImpl;
import cz.muni.fi.pa165.vozovna.dao.hibernate.UserDAOHibernateImpl;
import cz.muni.fi.pa165.vozovna.entities.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Persistence;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public class UserDAOTest {
    

    private static final String PERSISTENCE_UNIT_NAME = "VozovnaPU";
  
    public UserDAOTest() {
    }
    
    private UserDao createUserDAOFactory() {
        UserDAOHibernateImpl userDAO = new UserDAOHibernateImpl();
        userDAO.setFactory(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
        return userDAO;
    }
    private UserDao createUserDAOFactoryWithoutEMF() {
        return new UserDAOHibernateImpl();
    }
    
    @Test
    public void testGetUserByIdWithoutEMF(){
        // UserDAO without Entity Manager Factory
        UserDao dao = createUserDAOFactoryWithoutEMF();
        
        try {
            dao.create(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch(IllegalStateException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
       
    }
    
    @Test
    public void testGetUserByIdWithNullArgument(){
        // UserDAO without Entity Manager Factory
        UserDao dao = createUserDAOFactory();
        
        try {
             dao.getById(null);
             fail("Exception for null argument was not threw.");
        } catch(IllegalArgumentException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage() );
        }
    }
    
    
    @Test
    public void testValidCreateAndGetUserById(){
        UserDao dao = createUserDAOFactory();
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
        UserDao dao = createUserDAOFactoryWithoutEMF();
        
        try {
            dao.create(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch(IllegalStateException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage() );
        }
    }
    
    @Test
    public void testCreateWithNullArguments() {     
        UserDao dao = createUserDAOFactory();
        try {
            dao.create(null);
            fail("Exception for null argument was not threw.");
        } catch(IllegalArgumentException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage() );
        }
    }
    
//    @Test
//    public void testCreateWithWrongUser() {     
//        UserDao dao = createUserDAOFactory();
//        try {
//            dao.create(newDefaultUser());
//            fail("Exception for null argument was not threw.");
//        } catch(IllegalArgumentException e) {
//        } catch(Exception e) {
//            fail("Wrong Exception was threw: " + e + " " + e.getMessage() );
//        }
//    }

    
    @Test
    public void testUpdateWithoutEMF(){
        // UserDAO without Entity Manager Factory
        UserDao dao = createUserDAOFactoryWithoutEMF();
        
        try {
            dao.update(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch(IllegalStateException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
       
    }
    
    @Test
    public void testUpdateWithNullArgument(){
        // UserDAO without Entity Manager Factory
        UserDao dao = createUserDAOFactoryWithoutEMF();
        
        try {
            dao.update(null);
            fail("Exception for null argument was not threw.");
        } catch(IllegalArgumentException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
       
    }
    
    @Test
    public void testValidUpdate(){
        // UserDAO without Entity Manager Factory
        UserDao dao = createUserDAOFactory();
        User user = newUser("Petr Novak", UserClassEnum.EMPLOYEE, true);

        try {
            dao.create(user);
            user.setName("Jan Novy");
            dao.update(user);
            assertNotSame("Name hasn't been updated.", "Petr Novak", user.getName());
        } catch(Exception e) {
            fail("Unexcepted exception was threw: " + e + " " + e.getMessage());
        }
        
    }
    
    // REMOVE
    
    @Test
    public void testRemoveWithoutEMF() {

        UserDao dao = createUserDAOFactoryWithoutEMF();

        try {

            dao.remove(newDefaultUser());
            fail("Exception for null argument was not threw.");
        } catch(IllegalStateException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage() );
        }
    }
    
    @Test
    public void testRemoveWithNullArgument() {
        // Remove user that is null 
        UserDao dao = createUserDAOFactory();
        try {
            dao.remove(null);
            fail("Exception for null argument was not threw.");
        } catch(IllegalArgumentException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage() );
        }
    }
    
    
    @Test
    public void testValidRemove() {
        UserDao dao = createUserDAOFactory();
        User user = newDefaultUser();
        User result = null;
        try {
            dao.create(user);
            Long id = user.getId();
            dao.remove(user);
            result = dao.getById(id); 
            
        } catch(Exception e) {
            fail("Unexpected exception was threw: " + e + " " + e.getMessage());
        }
        assertNotNull("User was not removed. ", result);
            
    }
    
    
    // TEST FIND ALL
     
    @Test
    public void testFindAllWithoutEMF() {
        UserDao dao = createUserDAOFactoryWithoutEMF();
       
        try {
            dao.findAll();
            fail("Exception for null argument was not threw.");
        } catch(IllegalStateException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }
    
    @Test
    public void testValidFindAll() {
        UserDao dao = createUserDAOFactory();
        User user1 = newUser("Jan Novak", UserClassEnum.EMPLOYEE, false);
        User user2 = newUser("Petr Novak", UserClassEnum.EMPLOYEE, false);
        List<User> results = null;
        try {
            dao.create(user1);
            dao.create(user2);
            
            results = dao.findAll();
        } catch(Exception e) {
            fail("Unexpected exception was threw: " + e + " " + e.getMessage());
        }
        assertEquals("Unexpected number of results.", 2, results.size()); 
        
        List<User> expected = Arrays.asList(user1,user2);
        
        Collections.sort(results,idComparator);
        Collections.sort(expected,idComparator);
        
        assertEquals(expected, results);
        assertDeepEquals(expected, results);
     } 
    
    // FIND BY NAME
    
    @Test
    public void testFindByNameWithoutEMF() {
        UserDao dao = createUserDAOFactoryWithoutEMF();
        
        try {
            dao.findByName("Novak");
            fail("Exception for null argument was not threw.");
        } catch(IllegalStateException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
    }   
    
    @Test
    public void testFindByNameWithWrongArgument() {
        UserDao dao = createUserDAOFactory();
        
        try {
            dao.findByName(null);
            fail("Exception for null argument was not threw.");
        } catch(IllegalArgumentException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
        
        try {
            dao.findByName("");
            fail("Exception for empty string as argument was not threw.");
        } catch(IllegalArgumentException e) {
        } catch(Exception e) {
            fail("Wrong Exception was threw: " + e + " " + e.getMessage());
        }
     } 
    
    @Test
    public void testValidFindByName() {
        UserDao dao = createUserDAOFactory();
        User user = newUser("Jan Novak", UserClassEnum.EMPLOYEE, false);
        User user2 = newUser("Petr Novak", UserClassEnum.EMPLOYEE, false);
        List<User> all = null;
        List<User> results = null;
        try {
            dao.create(user);
            all = dao.findAll();
            dao.create(user2);
            
            results = dao.findByName(user.getName());
        } catch(Exception e) {
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
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getIsAdmin(), actual.getIsAdmin());
        assertEquals(expected.getUserClassId(), actual.getUserClassId());
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
        user.setName(name);
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
