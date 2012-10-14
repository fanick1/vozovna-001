package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.UserDao;
import cz.muni.fi.pa165.vozovna.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * @author Jozef Triscik
 */
public class UserDAOHibernateImpl implements UserDao {

    public EntityManagerFactory factory;
    
    public void setFactory(EntityManagerFactory factory) {
        if(factory == null) {
            throw new IllegalArgumentException("Given factory is null");
        }
        
        this.factory = factory;
    }
    
    @Override
    public User getById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Given id is null");
        }
        
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        
        return manager.find(User.class, id);
    }

    @Override
    public void create(User user) {
        if(user == null) {
            throw new IllegalArgumentException("Given user is null");
        }
        
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        
        transaction.begin();
        manager.persist(user);
        transaction.commit();
        
        manager.close();
    }

    public void remove(User user) {
        if(user == null) {
            throw new IllegalArgumentException("Given user is null");
        }
        
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        
        manager.remove(manager.merge(user));
        
        manager.close();
    }

    @Override
    public void update(User user) {
        if(user == null) {
            throw new IllegalArgumentException("Given user is null");
        }
        
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        
        transaction.begin();
        manager.persist(manager.merge(user));
        transaction.commit();
        
        manager.close();
    }

    @Override
    public List<User> findAll() {
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        TypedQuery<User> query = manager.createQuery("FROM VozovnaUser",User.class);
        List<User> result = query.getResultList();
        manager.close();
        
        return result;
    }
    
    @Override
    public List<User> findByName(String name) {
        if(name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        TypedQuery<User> query = manager.createQuery("FROM VozovnaUser u WHERE Name = :Name",User.class);
        query.setParameter("Name", name);
        List<User> result = query.getResultList();
        manager.close();
        
        return result;
    }
    
}
