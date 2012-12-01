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
import org.springframework.transaction.annotation.Transactional;

/**
 * Vehicle DAO using Hibernate
 *
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@Repository("vehicleDao")
public class VehicleDAOHibernateImpl extends GenericDAOHibernateImpl<Vehicle, Long> implements VehicleDAO {

    protected final Log logger = LogFactory.getLog(getClass());

    /*
     * (non-Javadoc) @see cz.muni.fi.pa165.vozovna.dao.VehicleDAO#findByUserClass(int)
     */
    @Override
    @Transactional
    public List<Vehicle> findByUserClass(UserClassEnum userClass) {
        if (userClass == null) {
            throw new IllegalArgumentException("userClass is null.");
        }

        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + Vehicle.class.getName() + " WHERE userClass = :USERCLASS ");
        query.setParameter("USERCLASS", userClass);
        return (List<Vehicle>) query.list();

    }

    @Override
    public List<Vehicle> getAvailableVehicles(User user, DateTime startDate, DateTime endDate) {

        if (user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Given dates cannot be null");
        }


        final Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(
                "FROM Vehicle h WHERE userClass = :USERCLASS "
                + "AND ((SELECT COUNT(*) FROM Drive AS d JOIN d.vehicle AS v WHERE h.id = v.id AND d.dateTo > :STARTDATE AND d.dateFrom < :STARTDATE) = 0) "
                + "AND ((SELECT COUNT(*) FROM Drive AS d JOIN d.vehicle AS v WHERE h.id = v.id AND d.dateTo > :ENDDATE AND d.dateFrom < :ENDDATE) = 0) AND ((SELECT COUNT(*) FROM Drive AS d JOIN d.vehicle AS v WHERE h.id = v.id AND d.dateTo < :ENDDATE AND d.dateFrom > :STARTDATE) = 0)");
        query.setParameter("USERCLASS", user.getUserClass());
        query.setParameter("STARTDATE", startDate);
        query.setParameter("ENDDATE", endDate);

        return (List<Vehicle>) query.list();
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
        logger.warn(query.toString());
        return (List<Vehicle>) query.list();
    }
}
