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
     * Finds all service intervals by vehicle.
     *
     * @return List of service intervals of vehicle.
     * @throws IllegalStateException Throws if factory was not initialized.
     * @throws IllegalArgumentException Throws if given vehicle is null;
     */
    public List<ServiceInterval> findAllByVehicle(Vehicle vehicle);
}
