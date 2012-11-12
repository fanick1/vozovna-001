package cz.muni.fi.pa165.vozovna.dao.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.entity.User;

/**
 * @author Eva Neduchalová
 */
@Stateless
public class UserDAOEJBImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void remove(User user) {
        em.remove(user);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<User> findByLastName(String lastName) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
