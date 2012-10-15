/**
 *  Copyright 2012 Frantisek Veverka, Eva Neduchalova, Jozef Triscik, Lukas Hajek
 *  The latest (and the greatest) version of this software can be obtained at 
 *	http://code.google.com/p/vozovna-001/
 *
 *  This file is part of Vozovna.
 *   Vozovna is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Vozovna is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Vozovna.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.muni.fi.pa165.vozovna.dao;

import cz.muni.fi.pa165.vozovna.entities.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;

/**
 * Vehicle DAO Interface
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
public interface VehicleDAO {
	
	
	/**
     * Returns Vehicle with given ID
     * 
	 * @param id        ID of Vehicle
	 * @return          The Vehicle with given ID, null if Vehicle doesn't exist
	 * @throws IllegalArgumentException     If id is null.
     * @throws IllegalStateException        If the Entity Manager Factory is not set.
     */
	public Vehicle getById(Long id);

	/**
     * Adds vehicle into system.
     * @param vehicle   The Vehicle to save
     * @throws IllegalArgumentException     If vehicle is null.
     * @throws IllegalStateException        If the Entity Manager Factory is not set.
     */
 	public void create(final Vehicle vehicle);
	
	/**
     * Removes vehicle
     * @param vehicle   The Vehicle to remove     
     * @throws IllegalArgumentException     If vehicle is null.
     * @throws IllegalStateException        If the Entity Manager Factory is not set.
     */
	public void remove(final Vehicle vehicle);
	
	/**
     * Updates vehicle
     * @param vehicle   The Vehicle to update
     * @throws IllegalArgumentException     If vehicle is null.
     * @throws IllegalStateException        If the Entity Manager Factory is not set.
     */
	public void update(final Vehicle vehicle);
	
	/**
     * Returns list with all of vehicles
     * @return java.util.List   List of vehicles
     * @throws IllegalStateException        If the Entity Manager Factory is not set.
     */
	public List<Vehicle> findAll();
	
	/**
     * Returns list with all of vehicles
     * @param userClass                     
     * @return java.util.List               List with vehicles
     * @throws IllegalArgumentException     If userClass is invalid
     * @throws IllegalStateException        If the Entity Manager Factory is not set.
     */
	public List<Vehicle> findByUserClass(UserClassEnum userClass);
}
