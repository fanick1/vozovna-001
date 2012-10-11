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

import cz.muni.fi.pa165.vozovna.entities.Drive;
import cz.muni.fi.pa165.vozovna.entities.User;

/**
 * stub
 * TODO FILL IN
 */
public interface DriveDAO {
	
	//TODO add comment
	public List<Drive> getById(Long id);

	//TODO add comment
	public void create(Drive drive);
	
	//TODO add comment
	public void remove(Drive drive);
	
	//TODO add comment
	public void update(Drive drive);
	
	//TODO add comment
	public List<Drive> findAll();
	
	//TODO add comment
	public List<Drive> findByUser(User user);
}