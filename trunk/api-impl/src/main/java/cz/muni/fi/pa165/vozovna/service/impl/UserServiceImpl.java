package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.dao.hibernate.ServiceIntervalDAOImpl;
import cz.muni.fi.pa165.vozovna.dao.hibernate.VehicleDAOHibernateImpl;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation User's Service Layer
 * 
 * @author Lukas Hajek (359617@mail.muni.cz)
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDAO userDAO;

	@Autowired
	private VehicleDAO vehicleDAO;

	@Autowired
	private ServiceIntervalDAO serviceIntervalDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("null id");
        }
        User user = userDAO.getById(id);
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public Long create(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException("user has set id");
        }
        User entity = user.toNewUser();
        entity.setPassword(passwordEncoder.encodePassword(user.getPassword(), ""));
        userDAO.create(entity);
        user.fromUser(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void remove(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("user unexists");
        }
        userDAO.remove(userDAO.getById(user.getId()));
        user.setId(null);
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("user has no id set");
        }
        User entity = userDAO.getById(user.getId());
        if (entity == null) {
            throw new IllegalArgumentException("user unexists");
        }

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setUserClass(user.getUserClass());
        entity.setIsAdmin(user.getIsAdmin());
        entity.setUsername(user.getUsername());

        if (entity.getPassword() != null && !entity.getPassword().equals(user.getPassword())) {
            // password changed
            entity.setPassword(passwordEncoder.encodePassword(user.getPassword(), ""));
        }

        entity.setEnabled(user.getEnabled());

        userDAO.update(entity);
        user.fromUser(entity);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        // find
        List<User> users = userDAO.findAll();

        // transform results
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(new UserDTO(user));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("null lastName");
        }

        // find
        List<User> users = userDAO.findByLastName(lastName);

        return convertListOfUsersToListOfUserDTOs(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        if (username == null) {
            throw new UsernameNotFoundException("Invalid username (null)");
        }

        User user = userDAO.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username.");
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("null id");
        }

        User user = userDAO.getByUsername(username);
        
        if (user == null) {
            return null;
        }

        return new UserDTO(user);
    }
    
    // @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findByCriteria(List<Criterion> criterion, List<Order> orders) {
        
        List<User> result = userDAO.findByCriteria(criterion, orders);
        
        return convertListOfUsersToListOfUserDTOs(result); 
    }

    /**
     * Converts list of vehicles to list of vehicle DTOs
     * 
     * @param list  List of vehicles
     * @return List of Drive Data Transform Objects
     */
    private static List<UserDTO> convertListOfUsersToListOfUserDTOs(List<User> entities) {
        List<UserDTO> result = new ArrayList<>();

        for (User item : entities) {
            result.add(new UserDTO(item));
        }

        return result;
    }
    
    /**
     * Uloží do DB dva uživatele: <br/>
     * user: admin, pass: admin, roles: ROLE_ADMIN <br/>
     * user: user, pass: user, roles: ROLE_USER
     */
    @Override
    @Transactional
    public void generateTestDataIfNoneExist() {

        if (userDAO.findAll().isEmpty()) {

            User newAdmin = new User();
            newAdmin.setUsername("admin");
            newAdmin.setPassword(passwordEncoder.encodePassword("admin", null));
            newAdmin.setFirstName("Helmut");
            newAdmin.setLastName("Instalatér");
            newAdmin.setIsAdmin(Boolean.TRUE);
            newAdmin.setUserClass(UserClassEnum.PRESIDENT);
            userDAO.create(newAdmin);

            User newUser = new User();
            newUser.setUsername("user");
            newUser.setPassword(passwordEncoder.encodePassword("user", null));
            newUser.setFirstName("Ingrid");
            newUser.setLastName("Zrovna jsem se sprchovala");
            newUser.setIsAdmin(Boolean.FALSE);
            newUser.setUserClass(UserClassEnum.EMPLOYEE);
            userDAO.create(newUser);

	        User president = new User();
	        president.setUsername("president");
	        president.setPassword(passwordEncoder.encodePassword("president", null));
	        president.setFirstName("Barack");
	        president.setLastName("Obama");
	        president.setIsAdmin(Boolean.FALSE);
	        president.setUserClass(UserClassEnum.PRESIDENT);
	        userDAO.create(president);

	        User manager = new User();
	        manager.setUsername("manager");
	        manager.setPassword(passwordEncoder.encodePassword("manager", null));
	        manager.setFirstName("John");
	        manager.setLastName("Goodman");
	        manager.setIsAdmin(Boolean.FALSE);
	        manager.setUserClass(UserClassEnum.MANAGER);
	        userDAO.create(manager);

	        User employee = new User();
	        employee.setUsername("employee");
	        employee.setPassword(passwordEncoder.encodePassword("employee", null));
	        employee.setFirstName("Brandon");
	        employee.setLastName("Frasier");
	        employee.setIsAdmin(Boolean.FALSE);
	        employee.setUserClass(UserClassEnum.EMPLOYEE);
	        userDAO.create(employee);

	        Vehicle fabia = new Vehicle();
	        fabia.setBrand("Škoda");
	        fabia.setDistanceCount(30000);
	        fabia.setEngineType("1.4 TDi");
	        fabia.setType("Fabia");
	        fabia.setUserClass(UserClassEnum.MANAGER);
	        fabia.setVin("147ef-5482c-664de");
	        fabia.setYearMade(2001);
	        if(vehicleDAO != null){
	            vehicleDAO.create(fabia);
	        }
	        else {
		        System.out.println("toto je skuska");
	        }

	        Vehicle octavia = new Vehicle();
	        octavia.setBrand("Škoda");
	        octavia.setDistanceCount(50000);
	        octavia.setEngineType("1.9 TDi 96 kW");
	        octavia.setType("Octavia");
	        octavia.setUserClass(UserClassEnum.PRESIDENT);
	        octavia.setVin("1xxef-12D3c-AA4de");
	        octavia.setYearMade(2010);
	        vehicleDAO.create(octavia);

	        Vehicle peugeot = new Vehicle();
	        peugeot.setBrand("Peugeot");
	        peugeot.setDistanceCount(30000);
	        peugeot.setEngineType("V6");
	        peugeot.setType("607");
	        peugeot.setUserClass(UserClassEnum.EMPLOYEE);
	        peugeot.setVin("154ef-4788c-613de");
	        peugeot.setYearMade(2003);
	        vehicleDAO.create(peugeot);

	        Vehicle tatra = new Vehicle();
	        tatra.setBrand("Tatra");
	        tatra.setDistanceCount(40000);
	        tatra.setEngineType("V12 T3-930");
	        tatra.setType("815");
	        tatra.setUserClass(UserClassEnum.EMPLOYEE);
	        tatra.setVin("1afef-ds82c-6134e");
	        tatra.setYearMade(1983);
	        vehicleDAO.create(tatra);

	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	        List<Date> dates = new ArrayList();
	        try {
		        dates.add(dateFormat.parse("17.5.2012"));
		        dates.add(dateFormat.parse("17.11.2012"));
	        } catch (Exception ex) {
		        // ignore
	        }

	        ServiceInterval interval = new ServiceInterval();
	        interval.setDated(dates);
	        interval.setDescription("Vymena koles");
	        interval.setInspectionInterval(183);
	        interval.setVehicle(fabia);
	        serviceIntervalDAO.create(interval);

	        dates = new ArrayList();
	        try {
		        dates.add(dateFormat.parse("3.1.2012"));
		        dates.add(dateFormat.parse("3.4.2012"));
		        dates.add(dateFormat.parse("3.7.2012"));
	        } catch (Exception ex) {
		        // ignore
	        }

	        interval = new ServiceInterval();
	        interval.setDated(dates);
	        interval.setDescription("Kontrola brzd");
	        interval.setInspectionInterval(90);
	        interval.setVehicle(octavia);
	        serviceIntervalDAO.create(interval);

	        dates = new ArrayList();
	        try {
		        dates.add(dateFormat.parse("3.1.2012"));
		        dates.add(dateFormat.parse("3.1.2013"));
	        } catch (Exception ex) {
		        // ignore
	        }

	        interval = new ServiceInterval();
	        interval.setDated(dates);
	        interval.setDescription("Kontrola nosnosti");
	        interval.setInspectionInterval(365);
	        interval.setVehicle(tatra);
	        serviceIntervalDAO.create(interval);

	        dates = new ArrayList();
	        try {
		        dates.add(dateFormat.parse("8.4.2012"));
		        dates.add(dateFormat.parse("8.4.2013"));
	        } catch (Exception ex) {
		        // ignore
	        }

	        interval = new ServiceInterval();
	        interval.setDated(dates);
	        interval.setDescription("Kontrola svetel");
	        interval.setInspectionInterval(365);
	        interval.setVehicle(peugeot);
	        serviceIntervalDAO.create(interval);

        }
    }

}
