package cz.muni.fi.pa165.vozovna.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("DriveServiceTest-context.xml")
public class DriveServiceTest {
	
	private User existingUser;
	
	private UserDTO existingUserDTO;
	
	private Vehicle existingVehicle;
	
	private Drive existingDrive;
	
	private DriveService driveService; 
	
	private DriveDAO driveDAO;
	
	private Long EXISTING_ID = 1l;

	private Long NON_EXISTING_ID = 2213123l;

	private DriveDTO genericDriveDto;
	
	private UserDTO genericUserDto;
	
	private VehicleDTO genericVehicleDto;
	@Before
	public void setUp(){
		final Set<Drive> tempStore = new HashSet<>();
		genericUserDto = new UserDTO();
		genericUserDto.setId(5l);
		genericUserDto.setFirstName("X");
		genericUserDto.setLastName("Y");
		genericUserDto.setUserClass(UserClassEnum.EMPLOYEE);
		genericUserDto.setIsAdmin(Boolean.FALSE);
		
		genericVehicleDto = new VehicleDTO();
		genericVehicleDto.setId(10l);
		genericVehicleDto.setBrand("B");
		genericVehicleDto.setDistanceCount(123);
		genericVehicleDto.setEngineType("E");
		genericVehicleDto.setType("T");
		genericVehicleDto.setUserClass(UserClassEnum.EMPLOYEE);
		genericVehicleDto.setVin("E123");
		genericVehicleDto.setYearMade(2000);
		
		genericDriveDto = new DriveDTO();
		genericDriveDto.setUser(genericUserDto);
		genericDriveDto.setVehicle(genericVehicleDto);
		genericDriveDto.setDateFrom(DateTime.now().minusDays(10));
		genericDriveDto.setDateTo(DateTime.now().minusDays(2));
		genericDriveDto.setDistance(Integer.valueOf(340));
		genericDriveDto.setState(DriveStateEnum.FINISHED);
		genericDriveDto.setId(15l);
		
		existingVehicle = new Vehicle();                  
		existingVehicle.setId(12l);                          
		existingVehicle.setBrand("B2");                       
		existingVehicle.setDistanceCount(1234);               
		existingVehicle.setEngineType("E2");                  
		existingVehicle.setType("T2");                        
		existingVehicle.setUserClass(UserClassEnum.EMPLOYEE);
		existingVehicle.setVin("E1234");                      
		existingVehicle.setYearMade(2005);                   
		
		existingUser = new User();
		existingUser.setFirstName("Pepa");
		existingUser.setLastName("Novak");
		existingUser.setIsAdmin(Boolean.FALSE);
		existingUser.setId(1l);
		existingUser.setUserClass(UserClassEnum.EMPLOYEE);
		
		existingUserDTO = new UserDTO(existingUser);
		
		existingDrive = new Drive();
		existingDrive.setDateFrom(new DateTime());
		existingDrive.setDateTo(new DateTime());
		existingDrive.setDistance(Integer.valueOf(15));
		existingDrive.setId(EXISTING_ID);
		existingDrive.setState(DriveStateEnum.RESERVED);
		existingDrive.setUser(existingUser);
		existingDrive.setVehicle(existingVehicle);
		
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
		         Drive update = (Drive) args[0];
		         existingDrive.setDateFrom(update.getDateFrom());
		         existingDrive.setDateTo(update.getDateTo());
		         existingDrive.setDistance(update.getDistance());
		         existingDrive.setState(update.getState());
		         existingDrive.setUser(update.getUser());
		         existingDrive.setVehicle(update.getVehicle());
		         return update;			}
			}
		).when(driveDAO).update(existingDrive);

		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
		         User u = (User) args[0];
		         List<Drive> result= new ArrayList<>();
		         for(Drive drive:tempStore){
		        	 if (drive.getUser().equals(u)){
		        		 result.add(drive);
		        	 }
		         }
		         if(result.size() > 0){
		        	 return result;
		         }else{
		        	 throw new IllegalArgumentException("Drive not found, User:" + u);
		         }
			}}
		).when(driveDAO).findByUser(any(User.class));

		
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
		         Drive newDrive = (Drive) args[0];
		         if(tempStore.contains(newDrive))
		         {
		        	 throw new IllegalArgumentException("Drive already present");
		         }
		         tempStore.add(newDrive);
		         
		         return "called with arguments: " + args;			}
			}
		).when(driveDAO).create(any(Drive.class));
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
		         Drive newDrive = (Drive) args[0];
		         if(!tempStore.contains(newDrive)){
		        	 throw new IllegalArgumentException("Already deleted");
		         }
		         tempStore.remove(newDrive);
		         newDrive.setId(null);
		         return "called with arguments: " + args;			}
			}
		).when(driveDAO).remove(any(Drive.class));

		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				 Object[] args = invocation.getArguments();
		         Long id = (Long) args[0];
		         for(Drive drive:tempStore){
		        	 if (drive.getId() == id){
		        		 return drive;
		        	 }
		         }
		         throw new IllegalArgumentException("Drive not found, id:" + id); 
			}}
		).when(driveDAO).getById(any(Long.class));
		
		when(driveDAO.findAll()).thenReturn(Arrays.asList(existingDrive));

		driveDAO.create(existingDrive);
	}

	@Autowired
	public void setDriveDAO(DriveDAO driveDAO) {
		this.driveDAO = driveDAO;
	}
	
	@Autowired
	public void setDriveService(DriveService driveService) {
		this.driveService = driveService;
	}
	
	@Test
	public void testGetById() {

		try{
			driveService.getById(null);
			fail("IllegalArgument exception expected");
		}catch(IllegalArgumentException e){
			//ok
		}
		
		DriveDTO d1 = driveService.getById(EXISTING_ID );
		assertNotNull("Existing record expected.", d1);
		assertEquals(EXISTING_ID,d1.getId());	
		DriveDTO d2 = null;
		try{
			d2 = driveService.getById(NON_EXISTING_ID);
			fail("Some exception expected.");
		}catch(Exception e){
			
		}
		assertNull("Existing record unexpected.", d2);
		
	}

	@Test
	public void testCreate() {
		
		try{
			driveService.create(null);
			fail("IllegalArgument exception expected");
		}catch(IllegalArgumentException e){
			//OK
		}
		
		Long id = driveService.create(genericDriveDto);
		assertNotNull(id);
		assertEquals("IDs should be equal.", id, genericDriveDto.getId());

		try{
			driveService.create(genericDriveDto);	//duplicity
			fail("Duplicit record shouldn't be possible.");
		}catch(Exception e){
			//OK?
		}
	}

	@Test
	public void testRemove() {

		try {
			driveService.remove(null);
			fail("IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
			//OK
		}
		
		
		DriveDTO drive = driveService.getById(EXISTING_ID);
		if(drive == null){
			throw new IllegalStateException("Testing data failure");
		}
		
		driveService.remove(drive);
		assertNull("Drive's ID should be null after deletion.",drive.getId());
		try{
			driveService.remove(drive);
			fail("Repeated deletion should throw DriveServiceFailureException");
		}catch(Exception e){
			
		}
		
		try{
			driveService.getById(EXISTING_ID);
			fail("The record should be deleted by now.");
		}catch(Exception e){
		}
		
		
	}

	@Test
	public void testUpdate() {
		DriveDTO drive = driveService.getById(EXISTING_ID);
		if(drive == null){
			throw new IllegalStateException("Testing data failure");
		}
		Integer oldDist = drive.getDistance();
		Integer newDist;
		if(oldDist == null){
			newDist = Integer.valueOf(10);
		}else{
			newDist = oldDist + 10;
		}

		drive.setDistance(newDist);
		
		try{
			driveService.update(null);
			fail("IllegalArgumentException expected.");
		}catch(IllegalArgumentException e){
			
		}
		
		DriveDTO afterUpdate = driveService.update(drive);
		DriveDTO afterUpdate2 = driveService.getById(drive.getId());
		assertEquals(afterUpdate, afterUpdate2);
		assertEquals(newDist, afterUpdate.getDistance());
		assertEquals(newDist, afterUpdate2.getDistance());
	}

	@Test
	public void testFindAll() {

		List<DriveDTO> x = driveService.findAll();
		assertNotNull(x);
		if(x.size() != 1){
			fail("Size of list expected: 1, got: " + x.size());
		}
	}

	@Test
	public void testFindByUser() {
		try{
			driveService.findByUser(null);
			fail("IllegalArgumentException expected.");
		}catch(IllegalArgumentException e){
			
		}
		UserDTO nonExisting = new UserDTO();
		
		try{
			driveService.findByUser(nonExisting);
			fail("Some exception expected.");
		}catch(Exception e)
		{
			
		}
		
//		List<DriveDTO> list = driveService.findByUser(existingUserDTO);
//		if(list.size() != 1){
//			fail("Size of list expected: 1, got: " + list.size());		
//        }
		
	}

}
