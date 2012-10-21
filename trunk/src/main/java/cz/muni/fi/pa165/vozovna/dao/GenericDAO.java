package cz.muni.fi.pa165.vozovna.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 * Base DAO with CRUD operations and findAll() method
 *
 * @author eva.neduchalova
 */
public interface GenericDAO<T, PK extends Serializable> {

    T getById(PK id);

    void create(T t);

    void update(T t);

    void remove(T t);

    List<T> findAll();

    void setEntityManagerFactory(EntityManagerFactory emf);
}