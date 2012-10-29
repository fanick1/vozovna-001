package cz.muni.fi.pa165.vozovna.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.vozovna.dao.GenericDAO;

/**
 * Hibernate implementation of base DAO with CRUD operations and findAll() method
 * 
 * @author eva.neduchalova
 */
public class GenericDAOHibernateImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {

    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager em;

    public GenericDAOHibernateImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * Sets Entity Manager
     * 
     * @param em
     *            The Entity Manager
     * @throws IllegalArgumentException
     *             If em is null.
     */
    public void setEm(EntityManager em) {
        if (em == null) {
            throw new IllegalArgumentException("Given manager cannot be null");
        }
        this.em = em;
    }

    @Override
    @Transactional
    public T getById(PK id) {
        return (T) em.find(entityClass, id);
    }

    @Override
    @Transactional
    public void create(T entity) {
        em.persist(entity);
        em.flush();
    }

    @Override
    @Transactional
    public void update(T entity) {
        em.merge(entity);
        em.flush();
    }

    @Override
    @Transactional
    public void remove(T entity) {
        em.remove(em.merge(entity));
        em.flush();
    }

    @Override
    @Transactional
    public List<T> findAll() {
        Query query = em.createQuery("FROM " + entityClass.getName() + " entity");
        return (List<T>) query.getResultList();
    }
}
