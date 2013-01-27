package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * Vehicle DAO using Hibernate
 * 
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@Repository("vehicleDao")
public class VehicleDAOHibernateImpl extends GenericDAOHibernateImpl<Vehicle, Long> implements VehicleDAO {

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public List<Vehicle> getAvailableVehicles(User user, DateTime startDate, DateTime endDate) {
        return getAvailableVehicles(user.getUserClass(), startDate, endDate);
    }

    @Override
    public List<Vehicle> getAvailableVehicles(UserClassEnum userClass, DateTime startDate, DateTime endDate) {

        if (userClass == null) {
            throw new IllegalArgumentException("Given userClass is null");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Given dates cannot be null");
        }

        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "FROM " + Vehicle.class.getName() + " v "
                + "WHERE v.userClass = :userClass "
                + "AND v NOT IN ("
                + "SELECT d.vehicle FROM " + Drive.class.getName() + " d WHERE "
                + "(d.state = :stateReserved OR d.state = :stateOngoing OR d.state = :stateFinished) "
                + "AND ( (:dateFrom >= d.dateFrom AND :dateFrom < d.dateTo) OR (:dateTo > d.dateFrom AND :dateTo <= d.dateTo) OR (:dateFrom < d.dateFrom AND :dateTo > d.dateTo) ) "
                + ")"
                + " AND v.maxDistance > (SELECT COALESCE(SUM(dr.distance),0) FROM " + Drive.class.getName() + " dr WHERE dr.vehicle = v AND dr.state = :stateFinished)"
                );
        query.setParameter("userClass", userClass);
        query.setParameter("dateFrom", startDate);
        query.setParameter("dateTo", endDate);
        query.setParameter("stateReserved", DriveStateEnum.RESERVED);
        query.setParameter("stateOngoing", DriveStateEnum.ONGOING);
        query.setParameter("stateFinished", DriveStateEnum.FINISHED);
        
        return (List<Vehicle>) query.list();
    }

    @Override
    public int getMileageOfVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Given vehicle is null");
        }

        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT SUM(d.distance) FROM " + Drive.class.getName()
                + " d WHERE d.vehicle=:vehicle AND d.state = :state");
        query.setParameter("vehicle", vehicle);
        query.setParameter("state", DriveStateEnum.FINISHED);

        Long sum = (Long) query.uniqueResult();

        return sum == null ? 0 : sum.intValue();
    }
}
