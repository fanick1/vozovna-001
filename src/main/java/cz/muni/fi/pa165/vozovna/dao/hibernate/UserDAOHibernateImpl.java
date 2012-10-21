package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * @author Jozef Triscik
 */
public class UserDAOHibernateImpl extends GenericDAOHibernateImpl<User, Long> implements UserDAO {

    @Override
    public List<User> findByName(String name) {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (this.emf == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.emf.createEntityManager();
        TypedQuery<User> query = manager.createQuery("FROM User u WHERE Name = :Name", User.class);
        query.setParameter("Name", name);
        List<User> result = query.getResultList();
        manager.close();

        return result;
    }
}
