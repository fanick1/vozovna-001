package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import java.util.List;

/**
 * Service Interval DAO
 *
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
public interface ServiceIntervalDAO extends GenericDAO<ServiceInterval, Long> {

    /**
     * Returns Service Interval with given ID
     *
     * @param id the ID of wanted ServiceInterval
     * @return ServiceInterval with given ID, null if ServiceInterval with given ID doesn't exist
     * @throws IllegalArgumentException if the id is null
     * @throws IllegalStateException Throws if factory was not initialized.
     */
    @Override
    public ServiceInterval getById(Long id);

    /**
     * Saves given Service Interval
     *
     * @param serviceInterval the ServiceInterval to save
     * @throws IllegalArgumentException if serviceInterval is null
     * @throws IllegalStateException Throws if factory was not initialized.
     */
    @Override
    public void create(ServiceInterval serviceInterval);

    /**
     * Updates given Service Interval
     *
     * @param serviceInterval the Service Interval to save
     * @throws IllegalArgumentException if serviceInterval is null
     * @throws IllegalStateException Throws if factory was not initialized.
     */
    @Override
    public void update(ServiceInterval serviceInterval);

    /**
     * Removes given service interval if exists.
     *
     * @param serviceInterval Interval to remove.
     * @throws IllegalArgumentException Throws if given service interval is null.
     * @throws IllegalStateException Throws if factory was not initialized.
     */
    @Override
    public void remove(ServiceInterval serviceInterval);

    /**
     * Returns list of all ServiceIntervals present in storage
     *
     * @param serviceInterval
     * @return java.util.List full of Service Intervals
     * @throws IllegalStateException Throws if factory was not initialized.
     */
    @Override
    public List<ServiceInterval> findAll();

    /**
     * Finds all service intervals by vehicle.
     *
     * @return List of service intervals of vehicle.
     * @throws IllegalStateException Throws if factory was not initialized.
     * @throws IllegalArgumentException Throws if given vehicle is null;
     */
    public List<ServiceInterval> findAllByVehicle(Vehicle vehicle);
    
}
