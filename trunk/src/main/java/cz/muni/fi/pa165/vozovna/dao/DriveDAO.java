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

import cz.muni.fi.pa165.vozovna.entities.Drive;
import cz.muni.fi.pa165.vozovna.entities.VozovnaUser;
import java.util.List;

/**
 * stub
 * TODO FILL IN
 */
public interface DriveDAO {
	
	/**
         * Return Drive with given id. Returns null if drive does not exists.
         * @param id
         * @return Drive if exists, null otherwise.
         * @throws IllegalArgumentException Throws if given id is null.
         * @throws IllegalStateException Throws if factory was not set.
         */
	public Drive getById(Long id);

	/**
         * Saves given drive into database.
         * @param drive Drive to save
         * @throws IllegalArgumentException Throws if given drive is null.
         * @throws IllegalStateException Throws if factory was not set.
         */
	public void create(Drive drive);
	
	/**
         * Removes given drive from database.
         * @param drive Drive to remove
         * @throws IllegalArgumentException Throws if given drive is null.
         * @throws IllegalStateException Throws if factory was not set.
         */
	public void remove(Drive drive);
	
	/**
         * Updates given drive into database.
         * @param drive Drive to update
         * @throws IllegalArgumentException Throws if given drive is null.
         * @throws IllegalStateException Throws if factory was not set.
         */
	public void update(Drive drive);
	
	/**
         * Find and return all drives in database.
         * @return List of all drives in database.
         * @throws IllegalStateException Throws if factory was not set.
         */
	public List<Drive> findAll();
	
	/**
         * Find and return drives of given user.
         * @param user VozovnaUser of which we want drives.
         * @return List of drives of given user.
         * @throws IllegalArgumentException Throws if given user is null.
         * @throws IllegalStateException Throws if factory was not set.
         */
	public List<Drive> findByUser(VozovnaUser user);
}
