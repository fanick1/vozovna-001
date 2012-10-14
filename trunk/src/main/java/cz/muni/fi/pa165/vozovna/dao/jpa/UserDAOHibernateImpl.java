package cz.muni.fi.pa165.vozovna.dao.jpa;

import cz.muni.fi.pa165.vozovna.dao.UserDAO;
import cz.muni.fi.pa165.vozovna.entities.VozovnaUser;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * @author Jozef Triscik
 */
public class UserDAOHibernateImpl implements UserDAO {

    public EntityManagerFactory factory;
    
    public void setFactory(EntityManagerFactory factory) {
        if(factory == null) {
            throw new IllegalArgumentException("Given factory is null");
        }
        
        this.factory = factory;
    }
    
    @Override
    public VozovnaUser getById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Given id is null");
        }
        
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        
        return manager.find(VozovnaUser.class, id);
    }

    @Override
    public void create(VozovnaUser user) {
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

    public void remove(VozovnaUser user) {
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
    public void update(VozovnaUser user) {
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
    public List<VozovnaUser> findAll() {
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        TypedQuery<VozovnaUser> query = manager.createQuery("FROM VozovnaUser",VozovnaUser.class);
        List<VozovnaUser> result = query.getResultList();
        manager.close();
        
        return result;
    }
    
    @Override
    public List<VozovnaUser> findByName(String name) {
        if(name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        if(this.factory == null) {
            throw new IllegalStateException("Factory is not initialized");
        }
        
        EntityManager manager = this.factory.createEntityManager();
        TypedQuery<VozovnaUser> query = manager.createQuery("FROM VozovnaUser u WHERE Name = :Name",VozovnaUser.class);
        query.setParameter("Name", name);
        List<VozovnaUser> result = query.getResultList();
        manager.close();
        
        return result;
    }
    
}
