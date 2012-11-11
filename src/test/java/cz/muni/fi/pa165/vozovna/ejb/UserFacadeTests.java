/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jostri
 */
public class UserFacadeTests {
    
    @EJB
    private UserFacade userFacade;
    
    public UserFacadeTests() {
    }
    
    @Before
    public void setUp() {
        this.userFacade = this.lookupUserFacadeBean();
        
//        this.userFacade = new UserFacade();
//        this.initDB();
    }
    
    @After
    public void tearDown() {
//        this.cleanDB();
    }
    
    @Test
     public void testGet() {
         
         if (this.userFacade == null) {
             Assert.fail("UserFacade wasn't initialiyed.");
         }
         
         List<EJBUser> users = this.userFacade.findAll();
         if(users == null || users.isEmpty()) {
             Assert.fail("Any entities of type EJBUser weren't loaded from database.");
         }
     }
    
     @Test
     public void testCreate() {
         
         if (this.userFacade == null) {
             Assert.fail("UserFacade wasn't initialiyed.");
         }
         
         EJBUser toCreate = UserFacadeTests.getUser("John", "Doe");
         this.userFacade.create(toCreate);
         
         if(this.userFacade.count() == 0) {
             Assert.fail("Loaded count of users is zero and should be more than zero.");
         }
     }
     
     private static EJBUser getUser(String firstName, String lastName) {
         EJBUser user = new EJBUser();
         user.setFirstName(firstName);
         user.setLastName(lastName);
         
         return user;
     }

    private UserFacade lookupUserFacadeBean() {
        try {
            Context c = new InitialContext();
//            return (UserFacade) c.lookup("java:global/cz.muni.fi.pa165_VehicleManagement_war_1.0-SNAPSHOT/UserFacade!cz.muni.fi.pa165.vozovna.ejb.UserFacade");
            return (UserFacade) c.lookup("cz.muni.fi.pa165.vozovna.ejb.UserFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
        
//        return new UserFacade();
    }
    
    private void initDB() {
        UserFacade uf = this.lookupUserFacadeBean();
        
        if (uf == null) {
            System.out.println("Create uf is null.");
        }
        
        uf.create(UserFacadeTests.getUser("John", "Doe"));
        uf.create(UserFacadeTests.getUser("George", "Lucas"));
        uf.create(UserFacadeTests.getUser("Liam", "Neeson"));
        uf.create(UserFacadeTests.getUser("James", "Bond"));
        
    }
    
    private void cleanDB() {
        UserFacade uf = this.lookupUserFacadeBean();
        
        List<EJBUser> users = uf.findAll();
        for (EJBUser user: users) {
            uf.remove(user);
        }
    }
}
