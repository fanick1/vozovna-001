package cz.muni.fi.pa165.vozovna.service.impl;

import cz.muni.fi.pa165.vozovna.dao.ServiceIntervalDAO;
import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.DevelopementDataGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Generator of developement data. Not to be used in production.
 *
 * @author Eva Neduchalova, uco 359893
 */
 @Service
public class DevelopementDataGeneratorImpl implements DevelopementDataGenerator {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private VehicleDAO vehicleDAO;
    @Autowired
    private ServiceIntervalDAO serviceIntervalDAO;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Generates test data including users admin (password admin) and user (password user)
     */
    @Transactional
    @Override
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
            fabia.setRegistrationPlate("1A2 1225");
            vehicleDAO.create(fabia);


            Vehicle octavia = new Vehicle();
            octavia.setBrand("Škoda");
            octavia.setDistanceCount(50000);
            octavia.setEngineType("1.9 TDi 96 kW");
            octavia.setType("Octavia");
            octavia.setUserClass(UserClassEnum.PRESIDENT);
            octavia.setVin("1xxef-12D3c-AA4de");
            octavia.setYearMade(2010);
            octavia.setRegistrationPlate("1B1 1111");
            vehicleDAO.create(octavia);

            Vehicle peugeot = new Vehicle();
            peugeot.setBrand("Peugeot");
            peugeot.setDistanceCount(30000);
            peugeot.setEngineType("V6");
            peugeot.setType("607");
            peugeot.setUserClass(UserClassEnum.EMPLOYEE);
            peugeot.setVin("154ef-4788c-613de");
            peugeot.setYearMade(2003);
            peugeot.setRegistrationPlate("2M3 2121");
            vehicleDAO.create(peugeot);

            Vehicle tatra = new Vehicle();
            tatra.setBrand("Tatra");
            tatra.setDistanceCount(40000);
            tatra.setEngineType("V12 T3-930");
            tatra.setType("815");
            tatra.setUserClass(UserClassEnum.EMPLOYEE);
            tatra.setVin("1afef-ds82c-6134e");
            tatra.setYearMade(1983);
            tatra.setRegistrationPlate("1B2 3456");
            vehicleDAO.create(tatra);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            List<Date> dates = new ArrayList<>();
            try {
                dates.add(dateFormat.parse("17.5.2012"));
                dates.add(dateFormat.parse("17.11.2012"));
            } catch (ParseException ex) {
                // ignore
            }

            ServiceInterval interval = new ServiceInterval();
            interval.setDated(dates);
            interval.setDescription("Výměna kol");
            interval.setInspectionInterval(183);
            interval.setVehicle(fabia);
            serviceIntervalDAO.create(interval);

            dates = new ArrayList<>();
            try {
                dates.add(dateFormat.parse("3.1.2012"));
                dates.add(dateFormat.parse("3.4.2012"));
                dates.add(dateFormat.parse("3.7.2012"));
            } catch (ParseException ex) {
                // ignore
            }

            interval = new ServiceInterval();
            interval.setDated(dates);
            interval.setDescription("Kontrola brzd");
            interval.setInspectionInterval(90);
            interval.setVehicle(octavia);
            serviceIntervalDAO.create(interval);

            dates = new ArrayList<>();
            try {
                dates.add(dateFormat.parse("3.1.2012"));
                dates.add(dateFormat.parse("3.1.2013"));
            } catch (ParseException ex) {
                // ignore
            }

            interval = new ServiceInterval();
            interval.setDated(dates);
            interval.setDescription("Kontrola nosnosti");
            interval.setInspectionInterval(365);
            interval.setVehicle(tatra);
            serviceIntervalDAO.create(interval);

            dates = new ArrayList<>();
            try {
                dates.add(dateFormat.parse("8.4.2012"));
                dates.add(dateFormat.parse("8.4.2013"));
            } catch (ParseException ex) {
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
