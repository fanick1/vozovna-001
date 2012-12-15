package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.entity.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jozef Triscik
 */
@Repository("userDao")
public class UserDAOHibernateImpl extends GenericDAOHibernateImpl<User, Long> implements UserDAO {

    @Override
    @Transactional
    public List<User> findByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("lastName cannot be null");
        }
        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + User.class.getName() 
                + " user WHERE user.lastName = :lastName");
        query.setParameter("lastName", lastName);
        return (List<User>) query.list();
    }

    /**
     * Returns User with given username. Returns null if such user does not exist.
     *
     * @author Eva Neduchalová
     * @param username username of demanded user
     * @return User if exists, null otherwise.
     * @throws IllegalArgumentException Throws if given username is null.
     */
    @Override
    public User getByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("null username");
        }
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }
}
