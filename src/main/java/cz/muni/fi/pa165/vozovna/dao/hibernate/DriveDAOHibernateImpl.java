package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

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
        List<Drive> result = null;
        TypedQuery<Drive> query = em.createQuery("FROM " + Drive.class.getName() + " d WHERE d.user = :user", Drive.class);
        result = query.setParameter("user", user).getResultList();
        return result;
    }
}
