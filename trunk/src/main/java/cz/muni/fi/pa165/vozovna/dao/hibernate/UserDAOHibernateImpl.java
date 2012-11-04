package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.entity.User;

/**
 * @author Jozef Triscik
 */
@Repository("userDao")
public class UserDAOHibernateImpl extends GenericDAOHibernateImpl<User, Long> implements UserDAO {

    @Override
    @Transactional
    public List<User> findByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        final Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + User.class.getName() + " user WHERE user.lastName = :lastName");
        query.setParameter("lastName", lastName);
        return (List<User>) query.list();
    }

}
