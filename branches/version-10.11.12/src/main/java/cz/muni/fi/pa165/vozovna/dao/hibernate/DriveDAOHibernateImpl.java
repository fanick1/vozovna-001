package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;

/**
 * Drive DAO using Hibernate.
 * 
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
@Repository("driveDao")
public class DriveDAOHibernateImpl extends GenericDAOHibernateImpl<Drive, Long> implements DriveDAO {

    /*
     * (non-Javadoc) @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#findByUser(cz.muni.fi.pa165.vozovna.entities.VozovnaUser)
     */
    @Override
    @Transactional
    public List<Drive> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + Drive.class.getName() + " d WHERE d.user = :user");
        query.setParameter("user", user);
        return (List<Drive>) query.list();
    }
}
