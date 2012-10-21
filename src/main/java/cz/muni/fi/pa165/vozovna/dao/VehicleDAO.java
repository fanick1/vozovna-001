package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;

/**
 * Vehicle DAO Interface
 *
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public interface VehicleDAO extends GenericDAO<Vehicle, Long> {

    /**
     * Returns Vehicle with given ID
     *
     * @param id ID of Vehicle
     * @return The Vehicle with given ID, null if Vehicle doesn't exist
     * @throws IllegalArgumentException If id is null.
     * @throws IllegalStateException If the Entity Manager Factory is not set.
     */
    @Override
    public Vehicle getById(Long id);

    /**
     * Adds vehicle into system.
     *
     * @param vehicle The Vehicle to save
     * @throws IllegalArgumentException If vehicle is null.
     * @throws IllegalStateException If the Entity Manager Factory is not set.
     */
    @Override
    public void create(final Vehicle vehicle);

    /**
     * Removes vehicle
     *
     * @param vehicle The Vehicle to remove
     * @throws IllegalArgumentException If vehicle is null.
     * @throws IllegalStateException If the Entity Manager Factory is not set.
     */
    @Override
    public void remove(final Vehicle vehicle);

    /**
     * Updates vehicle
     *
     * @param vehicle The Vehicle to update
     * @throws IllegalArgumentException If vehicle is null.
     * @throws IllegalStateException If the Entity Manager Factory is not set.
     */
    @Override
    public void update(final Vehicle vehicle);

    /**
     * Returns list of all vehicles
     *
     * @return java.util.List List of vehicles
     * @throws IllegalStateException If the Entity Manager Factory is not set.
     */
    @Override
    public List<Vehicle> findAll();

    /**
     * Returns list of vehicles that are assigned to given userClass
     *
     * @param userClass
     * @return java.util.List List with vehicles
     * @throws IllegalArgumentException If userClass is invalid
     * @throws IllegalStateException If the Entity Manager Factory is not set.
     */
    public List<Vehicle> findByUserClass(UserClassEnum userClass);
}
