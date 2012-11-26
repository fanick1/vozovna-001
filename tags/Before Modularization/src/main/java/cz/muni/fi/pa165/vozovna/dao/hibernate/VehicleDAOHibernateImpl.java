package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.dao.VehicleDAO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * Vehicle DAO using Hibernate
 * 
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@Repository("vehicleDao")
public class VehicleDAOHibernateImpl extends GenericDAOHibernateImpl<Vehicle, Long> implements VehicleDAO {

    @Autowired
    private DriveDAO driveDAO;

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

    public List<Vehicle> getAvailableVehicles(User user,  DateTime startDate, DateTime endDate) {

        if(user == null) {
            throw new IllegalArgumentException("Given user is null");
        }

        if(startDate == null || endDate == null) {
            throw new IllegalArgumentException("Given dates cannot be null");
        }

        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "FROM " + Vehicle.class.getName() + " AS v INNER JOIN " + Drive.class.getName() +
                        " AS d WHERE v.userClass = :USERCLASS AND d.dateTo > :DATETO AND d.dateFrom < :DATEFROM" +
                        " AND d.state <> :DRIVESTATEONE AND d.state <> :DRIVESTATETWO");
        query.setParameter("USERCLASS", user.getUserClass());
        query.setParameter("DATETO", startDate);
        query.setParameter("DATEFROM", endDate);
        query.setParameter("DRIVESTATEONE", "RESERVED");
        query.setParameter("DRIVESTATETWO", "RESERVED");

        return (List<Vehicle>) query.list();
    }
}
