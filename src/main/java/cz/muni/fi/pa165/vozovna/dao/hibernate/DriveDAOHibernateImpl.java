package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.DriveDAO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Drive DAO using Hibernate.
 *
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public class DriveDAOHibernateImpl extends GenericDAOHibernateImpl<Drive, Long> implements DriveDAO {

    /*
     * (non-Javadoc) @see
     * cz.muni.fi.pa165.vozovna.dao.DriveDAO#findByUser(cz.muni.fi.pa165.vozovna.entities.VozovnaUser)
     */
    @Override
    public List<Drive> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }
        if (this.emf == null) {
            throw new IllegalStateException("Entity Manager Factory is not set.");
        }
        EntityManager em = this.emf.createEntityManager();

        List<Drive> result = null;

        try {
            TypedQuery<Drive> query = em.createQuery("FROM " + Drive.class.getName() + " d WHERE d.user = :user", Drive.class);
            result = query.setParameter("user", user).getResultList();
        } catch (RuntimeException e) {
            //e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }

        return result;
    }
}
