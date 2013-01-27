package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.utils.EntityToDTOConvertor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.joda.time.DateTime;
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
public abstract class ServiceIntervalServiceTest {

    private ServiceIntervalService serviceIntervalService;
    private ServiceIntervalDAO serviceIntervalDao;
    private Vehicle existingVehicle;
    private final Long existingServiceIntervalID = 10l;
    private List<Date> serviced = Arrays.asList(DateTime.now().minusDays(10).toDate(), DateTime.now().minusDays(5).toDate());
    private VehicleDAO vehicleDAO;	//mock

    public void setServiceIntervalService(ServiceIntervalService serviceIntervalService) {
        this.serviceIntervalService = serviceIntervalService;
    }

    public void setServiceIntervalDao(ServiceIntervalDAO serviceIntervalDao) {
        this.serviceIntervalDao = serviceIntervalDao;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Before
    public void setup() {
        existingVehicle = new Vehicle();
        existingVehicle.setId(12l);
        existingVehicle.setBrand("B2");
        existingVehicle.setDistanceCount(1234);
        existingVehicle.setEngineType("E2");
        existingVehicle.setType("T2");
        existingVehicle.setUserClass(UserClassEnum.EMPLOYEE);
        existingVehicle.setVin("E1234");
        existingVehicle.setYearMade(2005);


        ServiceInterval existingServiceInterval = new ServiceInterval();
        final Set<ServiceInterval> tempStore = new HashSet<>();
        existingServiceInterval.setDated(serviced);
        existingServiceInterval.setDescription("");
        existingServiceInterval.setId(existingServiceIntervalID);
        existingServiceInterval.setInspectionInterval(40);
        existingServiceInterval.setVehicle(existingVehicle);

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Long id = (Long) args[0];
                if (id == existingVehicle.getId()) {
                    return existingVehicle;
                } else {
                    throw new IllegalArgumentException("Unknown vehicle with id " + id + ". Check implementation of this Mock.");
                }
            }
        }).when(vehicleDAO).getById(any(Long.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                ServiceInterval newServiceInterval = (ServiceInterval) args[0];
                if (tempStore.contains(newServiceInterval)) {
                    throw new IllegalArgumentException("ServiceInterval already present");
                }
                tempStore.add(newServiceInterval);

                return null;

            }
        }).when(serviceIntervalDao).create(any(ServiceInterval.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Arrays.asList(tempStore.toArray());
            }
        }).when(serviceIntervalDao).findAll();

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Vehicle vehicle = (Vehicle) args[0];
                List<ServiceInterval> result = new ArrayList<>();
                for (ServiceInterval serviceInterval : tempStore) {
                    if (serviceInterval.getVehicle().equals(vehicle)) {
                        result.add(serviceInterval);
                    }
                }
                if (result.size() > 0) {
                    return result;
                } else {
                    throw new IllegalArgumentException("Service Interval not found. Vehicle Id:" + vehicle.getId());
                }


            }
        }).when(serviceIntervalDao).findAllByVehicle(any(Vehicle.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Long id = (Long) args[0];

                for (ServiceInterval serviceInterval : tempStore) {
                    if (serviceInterval.getId() == id) {
                        return serviceInterval;
                    }
                }
                throw new IllegalArgumentException("Service Interval not found. Id:" + id);
            }
        }).when(serviceIntervalDao).getById(any(Long.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                ServiceInterval serviceInterval = (ServiceInterval) args[0];
                if (!tempStore.contains(serviceInterval)) {
                    throw new IllegalArgumentException("Already deleted. User:" + serviceInterval);
                }
                tempStore.remove(serviceInterval);
                serviceInterval.setId(null);
                return null;
            }
        }).when(serviceIntervalDao).remove(any(ServiceInterval.class));

        doAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                ServiceInterval serviceInterval = (ServiceInterval) args[0];
                ServiceInterval oldSI = serviceIntervalDao.getById(serviceInterval.getId());
                oldSI.setDated(serviceInterval.getDated());
                oldSI.setDescription(serviceInterval.getDescription());
                oldSI.setInspectionInterval(serviceInterval.getInspectionInterval());
                oldSI.setVehicle(serviceInterval.getVehicle());

                return null;
            }
        }).when(serviceIntervalDao).update(any(ServiceInterval.class));

        serviceIntervalDao.create(existingServiceInterval);
    }

    @Test
    public void testGetById() {
        if (null != serviceIntervalService.getById(null)) {
            fail("Null expected");
        }

        ServiceIntervalDTO s1 = serviceIntervalService.getById(existingServiceIntervalID);
        assertNotNull("Existing record expected.", s1);
        assertEquals(existingServiceIntervalID, s1.getId());
        ServiceIntervalDTO s2 = null;
        try {
            s2 = serviceIntervalService.getById(45435l);
            fail("Some exception expected.");
        } catch (Exception e) {
        }
        assertNull("Existing record unexpected.", s2);
    }

    @Test
    public void testCreate() {
        try {
            serviceIntervalService.create(null);
            fail("IllegalArgument exception expected");
        } catch (IllegalArgumentException e) {
            //OK
        }
        ServiceIntervalDTO serviceIntervalDto = new ServiceIntervalDTO();
        serviceIntervalDto.setId(15l);
        serviceIntervalDto.setInspectionInterval(213);
        serviceIntervalDto.setDescription("x");
        serviceIntervalDto.setVehicle(EntityToDTOConvertor.toDTO(existingVehicle));
        Long id = serviceIntervalService.create(serviceIntervalDto);
        assertNotNull(id);
        assertEquals("IDs should be equal.", id, serviceIntervalDto.getId());

        try {
            serviceIntervalService.create(serviceIntervalDto);	//duplicity
            fail("Duplicit records shouldn't be possible.");
        } catch (Exception e) {
            //OK?
        }
    }

    @Test
    public void testRemove() {

        try {
            serviceIntervalService.remove(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //OK
        }


        ServiceIntervalDTO serviceIntervalDTO = serviceIntervalService.getById(existingServiceIntervalID);
        if (serviceIntervalDTO == null) {
            throw new IllegalStateException("Testing data failure");
        }

        serviceIntervalService.remove(serviceIntervalDTO);
        assertNull("ServiceInterval's ID should be null after deletion.", serviceIntervalDTO.getId());
        try {
            serviceIntervalService.remove(serviceIntervalDTO);
            fail("Repeated deletion should throw ServiceIntervalFailureException");
        } catch (Exception e) {
        }

        try {
            serviceIntervalService.getById(existingServiceIntervalID);
            fail("The record should be deleted by now.");
        } catch (Exception e) {
        }

    }

    @Test
    public void testUpdate() {
        ServiceIntervalDTO serviceInterval = serviceIntervalService.getById(existingServiceIntervalID);
        if (serviceInterval == null) {
            throw new IllegalStateException("Testing data failure");
        }
        int oldInterval = serviceInterval.getInspectionInterval();
        int newInterval = 100;
        assertNotSame("This test is flawed.", oldInterval, newInterval);
        serviceInterval.setInspectionInterval(newInterval);

        try {
            serviceIntervalService.update(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
        }

        ServiceIntervalDTO afterUpdate = serviceIntervalService.update(serviceInterval);
        ServiceIntervalDTO afterUpdate2 = serviceIntervalService.getById(serviceInterval.getId());
        assertEquals(afterUpdate, afterUpdate2);
        assertEquals(newInterval, afterUpdate.getInspectionInterval());
        assertEquals(newInterval, afterUpdate2.getInspectionInterval());
    }

    @Test
    public void testFindAll() {
        List<ServiceIntervalDTO> x = serviceIntervalService.findAll();
        assertNotNull(x);
        if (x.size() != 1) {
            fail("Size of list expected: 1, got: " + x.size());
        }

    }
}
