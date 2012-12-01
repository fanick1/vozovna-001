package cz.muni.fi.pa165.vozovna.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

/**
 * Base DAO with CRUD operations and findAll() method
 *
 * @author eva.neduchalova
 */
public interface GenericDAO<T, PK extends Serializable> {

    /**
     * Returns an entity instance with given id. Returns null if such entity doesn`t exist.
     *
     * @param id
     * @return Entity instance with given id if exists, null otherwise.
     * @throws IllegalArgumentException Throws if given id is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    T getById(PK id);

    /**
     * Persists given entity instance in database.
     *
     * @param entity entity instance to save
     * @throws IllegalArgumentException Throws if given entity instance is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    void create(T entity);

    /**
     * Updates given entity in database.
     *
     * @param entity Entity to update
     * @throws IllegalArgumentException Throws if given entity is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    void update(T entity);

    /**
     * Removes given entity from database.
     *
     * @param entity Entity to remove
     * @throws IllegalArgumentException Throws if given entity is null.
     * @throws IllegalStateException Throws if factory is not set.
     */
    void remove(T entity);

    /**
     * Find and return all instances of this entity type in database.
     *
     * @return List of all entities in database.
     * @throws IllegalStateException Throws if factory is not set.
     */
    List<T> findAll();

    List<T> findByCriteria(List<Criterion> criterions, List<Order> orders);
}