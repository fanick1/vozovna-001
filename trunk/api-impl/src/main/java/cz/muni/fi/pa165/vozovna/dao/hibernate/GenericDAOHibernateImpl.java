package cz.muni.fi.pa165.vozovna.dao.hibernate;

import cz.muni.fi.pa165.vozovna.dao.GenericDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate implementation of base DAO with CRUD operations and findAll() method
 *
 * @author eva.neduchalova
 */
public abstract class GenericDAOHibernateImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {

    private Class<T> entityClass;
    @Resource(name = "sessionFactory")
    protected SessionFactory sessionFactory;

    public GenericDAOHibernateImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class) pt.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public T getById(PK id) {
        return (T) sessionFactory.getCurrentSession().get(entityClass, id);
    }

    @Override
    @Transactional
    public void create(T entity) {
        sessionFactory.getCurrentSession().save(entity);

    }

    @Override
    @Transactional
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    @Transactional
    public void remove(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    @Transactional
    public List<T> findAll() {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(entityClass);
        return crit.list();
    }

}
