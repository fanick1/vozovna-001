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
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
public class DriveDAOHibernateImpl implements DriveDAO {

	/**
	 * 
	 */
	public DriveDAOHibernateImpl() {
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#getById(java.lang.Long)
	 */
	@Override
	public List<Drive> getById(Long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#create(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void create(Drive drive) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#remove(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void remove(Drive drive) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#update(cz.muni.fi.pa165.vozovna.entities.Drive)
	 */
	@Override
	public void update(Drive drive) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#findAll()
	 */
	@Override
	public List<Drive> findAll() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	/* (non-Javadoc)
	 * @see cz.muni.fi.pa165.vozovna.dao.DriveDAO#findByUser(cz.muni.fi.pa165.vozovna.entities.User)
	 */
	@Override
	public List<Drive> findByUser(User user) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
