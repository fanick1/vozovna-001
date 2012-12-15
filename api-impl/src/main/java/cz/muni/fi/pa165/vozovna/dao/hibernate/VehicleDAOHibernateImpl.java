package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

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
                + "(d.state = :stateReserved OR d.state = :stateOngoing) "
                + "AND ( (:dateFrom > d.dateFrom AND :dateFrom < d.dateTo) OR (:dateTo > d.dateFrom AND :dateTo < d.dateTo) ) "
                + ")");
        query.setParameter("userClass", userClass);
        query.setParameter("dateFrom", startDate);
        query.setParameter("dateTo", endDate);
        query.setParameter("stateReserved", DriveStateEnum.RESERVED);
        query.setParameter("stateOngoing", DriveStateEnum.ONGOING);
        return (List<Vehicle>) query.list();
    }
}
