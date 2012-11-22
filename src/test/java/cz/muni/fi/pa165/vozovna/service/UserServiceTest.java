package cz.muni.fi.pa165.vozovna.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("UserServiceTest-context.xml")
public class UserServiceTest {

	@Autowired
	private  UserService userService; 
	
	@Autowired
	private  UserDAO userDao;

	private String existingUserLastName = "LAST";
	
	private final Long existingUserID = 1l;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	@Before
	public void setup(){
		User existingUser = new User();
		existingUser.setFirstName("F");
		existingUser.setId(existingUserID);
		existingUser.setIsAdmin(false);
		existingUser.setLastName(existingUserLastName);
		existingUser.setUserClass(UserClassEnum.EMPLOYEE);
		
		final Set<User> tempStore = new HashSet<>();
		
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
		         User newUser = (User) args[0];
		         if(tempStore.contains(newUser))
		         {
		        	 throw new IllegalArgumentException("User already present");
		         }
		         tempStore.add(newUser);

				return null;
			}
		}).when(userDao).create(any(User.class));
		
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return Arrays.asList(tempStore.toArray());
			}
		}).when(userDao).findAll();
		
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
		         String lastName = (String) args[0];
		         List<User> result = new ArrayList<>();
		         for(User user: tempStore){
		        	 if(user.getLastName().equalsIgnoreCase(lastName)){
		        		 result.add(user);
		        	 }
		         }
		         if(result.size() > 0){
		        	 return result;
		         }else{
		        	 throw new IllegalArgumentException("User not found. LastName:" + lastName);
		         }
		         
			}
		}).when(userDao).findByLastName(anyString());
				
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
		         Long id = (Long) args[0];

		         for(User user: tempStore){
		        	 if(user.getId() == id){
		        		 return user;
		        	 }
		         }
	        	 throw new IllegalArgumentException("User not found. Id:" + id);
		         
			}
		}).when(userDao).getById(any(Long.class));
		
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
		         User user = (User) args[0];
		         	if(!tempStore.contains(user)){
		         		throw new IllegalArgumentException("Already deleted. User:" + user);
		         	}
		         	tempStore.remove(user);
		         	user.setId(null);
		         	return null;
		         }
		}).when(userDao).remove(any(User.class));

		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
		         User user = (User) args[0];
		         User oldUser = userDao.getById(user.getId());
		         oldUser.setFirstName(user.getFirstName());
		         oldUser.setIsAdmin(user.getIsAdmin());
		         oldUser.setLastName(user.getLastName());
		         oldUser.setUserClass(user.getUserClass());
				return null;
			}
		}).when(userDao).update(any(User.class));
		
		userDao.create(existingUser);
	}
		

	
	@Test
	public void testGetById() {

//		if(null != userService.getById(null)){
//			fail("Null expected");
//		}
		try {
            userService.getById(null);
            fail("null id - IllegalArgumentException expected");
        } catch (IllegalArgumentException e){}
        
		UserDTO u1 = userService.getById(existingUserID);
		assertNotNull("Existing record expected.", u1);
		assertEquals(existingUserID,u1.getId());	
		UserDTO u2 = null;
		try{
			u2 = userService.getById(45435l);
			fail("Some exception expected.");
		}catch(Exception e){
			
		}
		assertNull("Existing record unexpected.", u2);

	}

	@Test
	public void testCreate() {
		try{
			userService.create(null);
			fail("IllegalArgument exception expected");
		}catch(IllegalArgumentException e){
			//OK
		}
		UserDTO userDto = new UserDTO();
		userDto.setId(15l);
        try {
            Long id = userService.create(userDto);
            fail("Create user with setted id should be forbidden.");
        } catch (IllegalArgumentException e) {}
//		assertNotNull(id);
//		assertEquals("IDs should be equal.", id, userDto.getId());
//
//		try{
//			userService.create(userDto);	//duplicity
//			fail("Duplicit records shouldn't be possible.");
//		}catch(Exception e){
//			//OK?
//		}
        
	}

	@Test
	public void testRemove() {

		try {
			userService.remove(null);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			//OK
		}
		
		
		UserDTO user = userService.getById(existingUserID);
		if(user == null){
			throw new IllegalStateException("Testing data failure");
		}
		
		userService.remove(user);
		assertNull("User's ID should be null after deletion.",user.getId());
		try{
			userService.remove(user);
			fail("Repeated deletion should throw UserServiceFailureException");
		}catch(Exception e){
			
		}
		
		try{
			userService.getById(existingUserID);
			fail("The record should be deleted by now.");
		}catch(Exception e){
		}
		
		
	}

	@Test
	public void testUpdate() {
		UserDTO user = userService.getById(existingUserID);
		if(user == null){
			throw new IllegalStateException("Testing data failure");
		}
		String oldFirstName = user.getFirstName();
		String newFirstName = "ABC";
		assertNotSame("This test is flawed.",oldFirstName, newFirstName);
		user.setFirstName(newFirstName);
		
		try{
			userService.update(null);
			fail("IllegalArgumentException expected.");
		}catch(IllegalArgumentException e){
			
		}
		
		UserDTO afterUpdate = userService.update(user);
		UserDTO afterUpdate2 = userService.getById(user.getId());
		assertEquals(afterUpdate, afterUpdate2);
		assertEquals(newFirstName, afterUpdate.getFirstName());
		assertEquals(newFirstName, afterUpdate2.getFirstName());
	}

	@Test
	public void testFindAll() {
		List<UserDTO> x = userService.findAll();
		assertNotNull(x);
		if(x.size() != 1){
			fail("Size of list expected: 1, got: " + x.size());
		}

	}

	@Test
	public void testFindByLastName() {
		try{
			userService.findByLastName(null);
			fail("IllegalArgumentException expected.");
		}catch(IllegalArgumentException e){
			
		}
		
		
		try{
			userService.findByLastName("");
			fail("Some exception expected.");
		}catch(Exception e)
		{
			
		}
		
		List<UserDTO> list = userService.findByLastName(existingUserLastName);
		if(list.size() != 1){
			fail("Size of list expected: 1, got: " + list.size());		}
		
	}

}

