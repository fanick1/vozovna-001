package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

/**
 * Vehicle Service
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public interface VehicleService {
    /**
     * Returns Vehicle with given id. Returns null if vehicle does not exists.
     * 
     * @param id        ID of vehicle
     * @return          VehicleDTO if Vehicle with given id exists, null otherwise.
     * @throws IllegalArgumentException     Throws if given id is null.
     * @throws DataAccessException          Throws if a data access exception occurred.
     */
    public VehicleDTO getById(Long id);

    /**
     * Saves given vehicle into database.
     * 
     * @param vehicle      Vehicle to save
     * @throws IllegalArgumentException     Throws if given vehicle is null.
     * @throws DataAccessException          Throws if a data access exception occurred.
     */
    public Long create(VehicleDTO vehicle);

    /**
     * Removes given vehicle from database.
     * 
     * @param vehicle      Vehicle to remove
     * @throws IllegalArgumentException     Throws if given vehicle is null.
     * @throws DataAccessException          Throws if a data access exception occurred.
     */
    public void remove(VehicleDTO vehicle);

    /**
     * Updates given vehicle in database.
     * 
     * @param vehicle      Vehicle to update
     * @throws IllegalArgumentException     Throws if given vehicle is null.
     * @throws DataAccessException          Throws if a data access exception occurred.
     */
    public VehicleDTO update(VehicleDTO vehicle);

    /**
     * Find and return all vehicles in database.
     * 
     * @return List of all vehicles.
     * @throws DataAccessException          Throws if a data access exception occurred.
     */
    public List<VehicleDTO> findAll();

    /**
     * Find and return vehicles with given lastname.
     * 
     * @param lastName      Lastname.
     * @return List of vehicles of given vehicle.
     * @throws IllegalArgumentException     Throws if given vehicle is null.
     * @throws DataAccessException          Throws if a data access exception occurred.
     */
    public List<VehicleDTO> findByUserClass(UserClassEnum userClass);
    
    
    public List<VehicleDTO> findByCriteria(List<Criterion> criterion, List<Order> orders);

    /**
     * Returns available cars for uesr between given dates.
     *
     * @param user        User, for which we want cars.
     * @param startDate   Date, from which we want reservate car.
     * @param endDate     Date, to which we want reservate car.
     *
     * @throws IllegalArgumentException If any of argument is null.
     */
    public List<VehicleDTO> getAvailableVehicles(User user,  DateTime startDate, DateTime endDate);
}