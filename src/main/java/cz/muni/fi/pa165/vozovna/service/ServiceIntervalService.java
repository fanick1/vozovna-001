package cz.muni.fi.pa165.vozovna.service;

import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import java.util.List;

/**
 * Serivice Interval Service 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public interface ServiceIntervalService {
    /**
     * Returns Service Interval with given id. Returns null if service interval does not exists.
     * 
     * @param id        ID of service interval
     * @return          ServiceIntervalDTO if Service Interval with given id exists, null otherwise.
     * @throws IllegalArgumentException                 Throws if given id is null.
     * @throws ServiceIntervalServiceFailureException   Throws if there is problem with service.
     */
    public ServiceIntervalDTO getById(Long id);

    /**
     * Saves given service interval into database.
     * 
     * @param serviceInterval      Service Interval to save
     * @throws IllegalArgumentException                 Throws if given service interval is null.
     * @throws ServiceIntervalServiceFailureException   Throws if there is problem with service.
     */
    public Long create(ServiceIntervalDTO serviceInterval);

    /** 
     * Removes given service interval from database.
     * 
     * @param serviceInterval      Service Interval to remove
     * @throws IllegalArgumentException                 Throws if given service interval is null.
     * @throws ServiceIntervalServiceFailureException   Throws if there is problem with service.
     */
    public void remove(ServiceIntervalDTO serviceInterval);

    /**
     * Updates given service interval in database.
     * 
     * @param serviceInterval      Service Interval to update
     * @throws IllegalArgumentException                 Throws if given service interval is null.
     * @throws ServiceIntervalServiceFailureException   Throws if there is problem with service.
     */
    public ServiceIntervalDTO update(ServiceIntervalDTO serviceInterval);

    /**
     * Find and return all service intervals in database.
     * 
     * @return List of all service intervals.
     * @throws ServiceIntervalServiceFailureException   Throws if there is problem with service.
     */
    public List<ServiceIntervalDTO> findAll();

    /**
     * Find and return service intervals with given lastname.
     * 
     * @param vehicle      Vehicle witch service intervals should be found.
     * @return List of service intervals of given vehicle.
     * @throws IllegalArgumentException                 Throws if given service interval is null.
     * @throws ServiceIntervalServiceFailureException   Throws if there is problem with service.
     */
    public List<ServiceIntervalDTO> findAllByVehicle(VehicleDTO vehicle);

}
