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

import java.util.List;

import cz.muni.fi.pa165.vozovna.entities.ServiceInterval;

/**
 * Service Interval DAO
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
public interface ServiceIntervalDAO {

	/**
	 * Returns Service Interval with given ID
	 * @param id the ID of wanted ServiceInterval
	 * @return ServiceInterval with given ID, 
	 * null if ServiceInterval with given ID doesn't exist
	 * @throws IllegalArgumentException if the id is null
	 */
	public ServiceInterval getById(Long id);
	
	/**
	 * Saves given Service Interval
	 * @param serviceInterval the Service Interval to save
	 * @throws IllegalArgumentException if serviceInterval is null
	 */
	public void create(ServiceInterval serviceInterval);
	
	/**
	 * Updates given Service Interval
	 * @param serviceInterval the Service Interval to save
	 * @throws IllegalArgumentException if serviceInterval is null
	 */
	public void update(ServiceInterval serviceInterval);
	
	/**
	 * Returns list of all Service Intervals present in storage
	 * @param serviceInterval 
	 * @return java.util.List full of Service Intervals
	 */
	public List<ServiceInterval> findAll();
}