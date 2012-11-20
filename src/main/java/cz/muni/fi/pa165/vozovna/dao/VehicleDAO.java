package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;

/**
 * Vehicle DAO Interface
 *
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public interface VehicleDAO extends GenericDAO<Vehicle, Long> {

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
