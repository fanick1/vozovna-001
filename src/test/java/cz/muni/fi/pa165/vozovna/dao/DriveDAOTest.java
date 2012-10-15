///**
// *  Copyright 2012 Frantisek Veverka, Eva Neduchalova, Jozef Triscik, Lukas Hajek
// *  The latest (and the greatest) version of this software can be obtained at 
// *	http://code.google.com/p/vozovna-001/
// *
// *  This file is part of Vozovna.
// *   Vozovna is free software: you can redistribute it and/or modify
// *  it under the terms of the GNU General Public License as published by
// *  the Free Software Foundation, either version 3 of the License, or
// *  (at your option) any later version.
// *
// *  Vozovna is distributed in the hope that it will be useful,
// *  but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  GNU General Public License for more details.
// *
// *  You should have received a copy of the GNU General Public License
// *  along with Vozovna.  If not, see <http://www.gnu.org/licenses/>.
// */
//package cz.muni.fi.pa165.vozovna.dao;
//
//import static org.junit.Assert.*;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import cz.muni.fi.pa165.vozovna.dao.hibernate.DriveDAOHibernateImpl;
//import cz.muni.fi.pa165.vozovna.entities.Drive;
//import cz.muni.fi.pa165.vozovna.entities.Vehicle;
//import cz.muni.fi.pa165.vozovna.entities.User;
//import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
//import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
//
//import org.joda.time.DateTime;
///**
// * @author Frantisek Veverka, 207422@mail.muni.cz
// */
//public class DriveDAOTest {
//
//	
//	private DriveDAO driveDao;
//	
//	private static Long driveId1 = 1l;
//
//	private Vehicle vehicle;
//
//	private User franta;
//
//	private Drive drive;
//
//	private EntityManagerFactory emf;
//	
//	public DriveDAOTest() {
//		emf = Persistence.createEntityManagerFactory("VozovnaPU");
//	}
//	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {        
//		EntityManager localManager = emf.createEntityManager();
//		localManager.getTransaction().begin();
//		
//		fillData(localManager);
//
//        DriveDAOHibernateImpl driveDaoHibernate = new DriveDAOHibernateImpl();
//        driveDaoHibernate.setEntityManagerFactory(emf);
//        driveDao = driveDaoHibernate;
//	}
//
//	private void fillData(EntityManager localManager) {
//		drive = new Drive();
//        vehicle = new Vehicle();
//        vehicle.setBrand("Skoda");
//        vehicle.setDistanceCount(191400);
//        vehicle.setEngineType("1.3 MPI");
//        vehicle.setType("Felicia GLX");
//        vehicle.setUserClass(UserClassEnum.EMPLOYEE);
//        vehicle.setVin("13EBF1893241");
//        vehicle.setYearMade(1998);
//        localManager.persist(vehicle);
//        franta = new User();
//        franta.setIsAdmin(true);
//        franta.setName("BFU");
//        franta.setUserClass(UserClassEnum.EMPLOYEE);
//        localManager.persist(franta);
//        drive.setDateFrom(DateTime.now().minusDays(1));
//        drive.setDateFrom(DateTime.now());
//        drive.setDistance(Integer.valueOf(100));
//        drive.setState(DriveStateEnum.RESERVED);
//        drive.setUser(franta);
//        drive.setVehicle(vehicle);
////        drive.setId(driveId1);
//        
//        localManager.persist(drive);
//        driveId1 = drive.getId();	//store for testing purposes
//        localManager.getTransaction().commit();
//        localManager.close();
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//		
//	}
//
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#getById(java.lang.Long)}.
//	 */
//	@Test
//	public void testGetById1() {
//		try{
//			driveDao.getById(null);
//			fail("IllegalArgumentException expected.");
//		}catch(IllegalArgumentException e){
//			//OK
//		}
//	}
//
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#getById(java.lang.Long)}.
//	 */
//	@Test
//	public void testGetById2() {
//		assertNull("null expected",driveDao.getById(-1l));
//	}
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#getById(java.lang.Long)}.
//	 */
//	@Test
//	public void testGetById3() {		
//		Drive test1 = driveDao.getById(driveId1);
//		assertNotNull(test1);
//		
//		assertEquals(drive, test1);		
//		assertEquals(franta, test1.getUser());
//		assertEquals(vehicle, test1.getVehicle());
//	}
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#create(cz.muni.fi.pa165.vozovna.entities.Drive)}.
//	 */
//	@Test
//	public void testCreate1() {
//		try{
//			driveDao.create(null);
//			fail("IllegalArgumentException expected");
//		}catch(IllegalArgumentException e)
//		{
//			//OK
//		}
//	}
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#create(cz.muni.fi.pa165.vozovna.entities.Drive)}.
//	 */
//	@Test
//	public void testCreate2() {
//		Drive newDrive = new Drive();
//		
//		driveDao.create(newDrive);
//		//OK
//	}
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#create(cz.muni.fi.pa165.vozovna.entities.Drive)}.
//	 */
//	@Test
//	public void testCreate3() {
//		try{
//			driveDao.create(drive);	//duplicity
//			fail("Some exception expected");
//		}catch(Exception e){
//			//OK
//		}
//		
//	}
//
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#remove(cz.muni.fi.pa165.vozovna.entities.Drive)}.
//	 */
//	@Test
//	public void testRemove() {
//		try{
//			driveDao.remove(null);
//			fail("IllegalArgumentException expected");
//		}catch(IllegalArgumentException e){
//			//OK
//		}
//	}
//
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#update(cz.muni.fi.pa165.vozovna.entities.Drive)}.
//	 */
//	@Test
//	public void testUpdate1() {
//		try{
//			driveDao.getById(null);
//			fail("IllegalArgumentException expected");
//		}catch(IllegalArgumentException e){
//			//OK
//		}
//		
//		
//	}
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#update(cz.muni.fi.pa165.vozovna.entities.Drive)}.
//	 */
//	@Test
//	public void testUpdate2() {
//		
//		Integer distance = 1234;
//		DriveStateEnum state = DriveStateEnum.FINISHED;
//		User user = new User();
//		Vehicle vehicle2 = new Vehicle();
//		
//		Drive existingDrive = driveDao.getById(drive.getId());
//		existingDrive.setDistance(distance);
//		existingDrive.setState(state);
//		existingDrive.setUser(user);
//		existingDrive.setVehicle(vehicle2);
//	
//		driveDao.update(existingDrive);
//		
//		Drive existingDrive2 = driveDao.getById(drive.getId());
//		assertEquals(distance, existingDrive2.getDistance());
//		assertEquals(state, existingDrive2.getState());
//		assertEquals(user, existingDrive2.getUser());
//		assertEquals(vehicle2, existingDrive2.getVehicle());
//	}
//	
//
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#findAll()}.
//	 */
//	@Test
//	public void testFindAll() {
//		List<Drive> list = driveDao.findAll();
//		if(list == null){
//			fail("Non null list expected");
//		}
//		if(list.size() != 1){
//			fail("List of size 1 expected");
//		}
//		assertEquals(drive,list.get(0));
//	}
//
//	/**
//	 * Test method for {@link cz.muni.fi.pa165.vozovna.dao.DriveDAO#findByUser(cz.muni.fi.pa165.vozovna.entities.User)}.
//	 */
//	@Test
//	public void testFindByUser() {
//		
//		try{
//			driveDao.findByUser(null);
//			fail("IllegalArgumentException expected");
//		}catch(IllegalArgumentException e){
//			//OK
//		}
//		driveDao.findByUser(franta);
//	}
//
//}
