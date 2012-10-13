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
package cz.muni.fi.pa165.vozovna.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * The Entity represents service interval. 
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
@Entity
public class ServiceInterval implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
	 * Unique users ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    /** 
     * The required time interval between two service inspections (in days).  
     */
    @Column(nullable = false)
    private int inspectionInterval;
    
    /**
     * The dates when vehicle was inspected.
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private List<Date> dated;
    
    /**
     * The related vehicle 
     */
    @Column(nullable = false)
    @ManyToOne()
    private Vehicle vehicle;
    
    /**
     * Description of service interval. E.g: wheel exchange
     */
    @Column(nullable=false)
    private String description;

    
    /**
     * @return      Service interval ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id    Service interval ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    public int getInspectionInterval() {
        return inspectionInterval;
    }

    /**
     * Sets time interval between two service inspections 
     * @param inspectionInterval            Interval between two service inspections (in days)
     * @throws IllegalArgumentException     If inspectionInterval is less or equals to 0
     */
    public void setInspectionInterval(int inspectionInterval) {
        if (inspectionInterval <= 0) {
            throw new IllegalArgumentException("inspectionInterval");
        }
        this.inspectionInterval = inspectionInterval;
    }
    /**
     * @return      List of dates when vehicle was inspected.
     */
    public List<Date> getDated() {
        return dated;
    }

    public void setDated(List<Date> dated) {
        this.dated = dated;
    }
    
    /**
     * @return      The connected vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @param   vehicle                      The related vehicle
     * @throws  IllegalArgumentException     When the vehicle is null
     */
    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle");
        }
        this.vehicle = vehicle;
    }

    /**
     * @return  Description of service interval.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param   description                 Description of service interval.
     * @throws IllegalArgumentException     When description is null
     */
    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("description");
        }
        this.description = description;
    }

    @Override
    public String toString() {
        return "ServiceInterval{" + "id=" + id + ", inspectionInterval=" + inspectionInterval + ", dated=" + dated + ", vehicle=" + vehicle + ", description=" + description + '}';
    }
 
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getId() == null) {
            return false;
        }
        if (obj instanceof ServiceInterval) {
            return getId().equals(((ServiceInterval) obj).getId());
        }
        return false;
    }
    
	
}
