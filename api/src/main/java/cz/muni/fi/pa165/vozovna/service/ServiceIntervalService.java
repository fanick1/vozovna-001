package cz.muni.fi.pa165.vozovna.service;

import java.util.List;

import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;

/**
 * Serivice Interval Service
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public interface ServiceIntervalService {

    /**
     * Returns Service Interval with given id. Returns null if service interval does not exists.
     * 
     * @param id ID of service interval
     * @return ServiceIntervalDTO if Service Interval with given id exists, null otherwise.
     * @throws IllegalArgumentException Throws if given id is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public ServiceIntervalDTO getById(Long id);

    /**
     * Saves given service interval into database.
     * 
     * @param serviceInterval Service Interval to save
     * @throws IllegalArgumentException Throws if given service interval is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public Long create(ServiceIntervalDTO serviceInterval);

    /**
     * Removes given service interval from database.
     * 
     * @param serviceInterval Service Interval to remove
     * @throws IllegalArgumentException Throws if given service interval is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public void remove(ServiceIntervalDTO serviceInterval);

    /**
     * Updates given service interval in database.
     * 
     * @param serviceInterval Service Interval to update
     * @throws IllegalArgumentException Throws if given service interval is null.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public ServiceIntervalDTO update(ServiceIntervalDTO serviceInterval);

    /**
     * Find and return all service intervals in database.
     * 
     * @return List of all service intervals.
     * @throws DataAccessException Throws if a data access exception occurred.
     */
    public List<ServiceIntervalDTO> findAll();
}
