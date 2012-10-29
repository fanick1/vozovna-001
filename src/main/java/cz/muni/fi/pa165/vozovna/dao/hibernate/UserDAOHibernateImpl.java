package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

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
        if (lastName == null || "".equals(lastName)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        TypedQuery<User> query = em.createQuery("FROM " + User.class.getName() + " u WHERE lastName = :Name", User.class);
        query.setParameter("Name", lastName);
        List<User> result = query.getResultList();
        return result;
    }
}
