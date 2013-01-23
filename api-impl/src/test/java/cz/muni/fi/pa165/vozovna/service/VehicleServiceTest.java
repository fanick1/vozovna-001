package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
public abstract class VehicleServiceTest {

    private VehicleService vehicleService;
    private VehicleDAO vehicleDao;
    private Vehicle existingVehicle;
    private final Long existingVehicleID = 1l;

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void setVehicleDao(VehicleDAO vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Before
    public void setup() {
        final Set<Vehicle> tempStore = new HashSet<>();

        existingVehicle = new Vehicle();
        existingVehicle.setBrand("B");
        existingVehicle.setDistanceCount(23);
        existingVehicle.setEngineType("ET");
        existingVehicle.setId(existingVehicleID);
        existingVehicle.setType("T1");
        existingVehicle.setUserClass(UserClassEnum.EMPLOYEE);
        existingVehicle.setVin("V1");
        existingVehicle.setYearMade(2000);

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Vehicle newVehicle = (Vehicle) args[0];
                if (tempStore.contains(newVehicle)) {
                    throw new IllegalArgumentException("Vehicle already present");
                }
                tempStore.add(newVehicle);

                return null;
            }
        }).when(vehicleDao).create(any(Vehicle.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Arrays.asList(tempStore.toArray());
            }
        }).when(vehicleDao).findAll();

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Long id = (Long) args[0];

                for (Vehicle vehicle : tempStore) {
                    if (vehicle.getId() == id) {
                        return vehicle;
                    }
                }
                throw new IllegalArgumentException("Vehicle not found. Id:" + id);

            }
        }).when(vehicleDao).getById(any(Long.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Vehicle vehicle = (Vehicle) args[0];
                if (!tempStore.contains(vehicle)) {
                    throw new IllegalArgumentException("Already deleted. Vehicle:" + vehicle);
                }
                tempStore.remove(vehicle);
                vehicle.setId(null);
                return null;
            }
        }).when(vehicleDao).remove(any(Vehicle.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Vehicle vehicle = (Vehicle) args[0];
                Vehicle oldVehicle = vehicleDao.getById(vehicle.getId());
                oldVehicle.setBrand(vehicle.getBrand());
                oldVehicle.setDistanceCount(vehicle.getDistanceCount());
                oldVehicle.setEngineType(vehicle.getEngineType());
                oldVehicle.setType(vehicle.getType());
                oldVehicle.setUserClass(vehicle.getUserClass());
                oldVehicle.setVin(vehicle.getVin());
                oldVehicle.setYearMade(vehicle.getYearMade());
                return oldVehicle;
            }
        }).when(vehicleDao).update(any(Vehicle.class));

        vehicleDao.create(existingVehicle);


    }

    @Test
    public void testGetById() {

        if (null != vehicleService.getById(null)) {
            fail("Null expected");
        }

        VehicleDTO u1 = vehicleService.getById(existingVehicleID);
        assertNotNull("Existing record expected.", u1);
        assertEquals(existingVehicleID, u1.getId());
        VehicleDTO u2 = null;
        try {
            u2 = vehicleService.getById(45435l);
            fail("Some exception expected.");
        } catch (Exception e) {
        }
        assertNull("Existing record unexpected.", u2);

    }

    @Test
    public void testCreate() {
        try {
            vehicleService.create(null);
            fail("IllegalArgument exception expected");
        } catch (IllegalArgumentException e) {
            //OK
        }
        VehicleDTO vehicleDto = new VehicleDTO(existingVehicle);
        vehicleDto.setId(15l);
        Long id = vehicleService.create(vehicleDto);
        assertNotNull(id);
        assertEquals("IDs should be equal.", id, vehicleDto.getId());

        try {
            vehicleService.create(vehicleDto);	//duplicity
            fail("Duplicit records shouldn't be possible.");
        } catch (Exception e) {
            //OK?
        }
    }

    @Test
    public void testRemove() {

        try {
            vehicleService.remove(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //OK
        }


        VehicleDTO vehicle = vehicleService.getById(existingVehicleID);
        if (vehicle == null) {
            throw new IllegalStateException("Testing data failure");
        }

        vehicleService.remove(vehicle);
        assertNull("Vehicle's ID should be null after deletion.", vehicle.getId());
        try {
            vehicleService.remove(vehicle);
            fail("Repeated deletion should throw VehicleServiceFailureException");
        } catch (Exception e) {
        }

        try {
            vehicleService.getById(existingVehicleID);
            fail("The record should be deleted by now.");
        } catch (Exception e) {
        }


    }

    @Test
    public void testUpdate() {
        VehicleDTO vehicle = vehicleService.getById(existingVehicleID);
        if (vehicle == null) {
            throw new IllegalStateException("Testing data failure");
        }
        String oldVin = vehicle.getVin();
        String newVin = "ABC";
        assertNotSame("This test is flawed.", oldVin, newVin);
        vehicle.setVin(newVin);

        try {
            vehicleService.update(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
        }

        VehicleDTO afterUpdate = vehicleService.update(vehicle);
        VehicleDTO afterUpdate2 = vehicleService.getById(vehicle.getId());
        assertEquals(afterUpdate, afterUpdate2);
        assertEquals(newVin, afterUpdate.getVin());
        assertEquals(newVin, afterUpdate2.getVin());
    }

    @Test
    public void testFindAll() {
        List<VehicleDTO> x = vehicleService.findAll();
        assertNotNull(x);
        if (x.size() != 1) {
            fail("Size of list expected: 1, got: " + x.size());
        }

    }
}
