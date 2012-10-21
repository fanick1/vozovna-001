package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.GenericDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Hibernate implementation of base DAO with CRUD operations and findAll() method
 *
 * @author eva.neduchalova
 */
public class GenericDAOHibernateImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {

    private Class< T> entityClass;
    public EntityManagerFactory emf;

    public GenericDAOHibernateImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * Sets Entity Manager Factory
     *
     * @param emf The Entity Manager Factory
     * @throws IllegalArgumentException If emf is null.
     */
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        if (emf == null) {
            throw new IllegalArgumentException("Given factory cannot be null");
        }
        this.emf = emf;
    }

    @Override
    public T getById(PK id) {
        if (id == null) {
            throw new IllegalArgumentException("null entity id");
        }
        if (emf == null) {
            throw new IllegalStateException("Factory is not initialized!");
        }
        EntityManager em = emf.createEntityManager();
        T result = null;
        try {
            result = em.find(entityClass, id);
        } finally {
            em.close();
        }
        return result;
    }

    @Override
    public void create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        if (emf == null) {
            throw new IllegalStateException("Factory is not initialized!");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        if (emf == null) {
            throw new IllegalStateException("Factory is not initialized!");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        if (emf == null) {
            throw new IllegalStateException("Factory is not initialized!");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        if (emf == null) {
            throw new IllegalStateException("Factory is not initialized!");
        }
        EntityManager em = emf.createEntityManager();
        List<T> result = null;
        try {
            Query query = em.createQuery("FROM " + entityClass.getName() + " entity");
            result = (List<T>) query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}