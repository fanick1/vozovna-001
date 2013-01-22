package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;
import org.joda.time.DateTime;

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
     * Returns available cars for uesr between given dates.
     *
     * @param user User, for which we want cars.
     * @param startDate Date, from which we want reservate car.
     * @param endDate Date, to which we want reservate car.
     *
     * @throws IllegalArgumentException If any of argument is null.
     */
    public List<Vehicle> getAvailableVehicles(User user, DateTime startDate, DateTime endDate);

    public List<Vehicle> getAvailableVehicles(UserClassEnum userClass, DateTime startDate, DateTime endDate);
    
    /**
     * Returns total km went by vehicle until now.
     * 
     * @param vehicle Vehicle, for which compute mileage.
     * @return Mileage of vehicle
     * @throws IllegalArgumentException Throws if given vehicle is null.
     */
    public int getMileageOfVehicle(Vehicle vehicle);
}
