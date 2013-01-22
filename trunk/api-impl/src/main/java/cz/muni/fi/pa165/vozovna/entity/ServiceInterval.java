package cz.muni.fi.pa165.vozovna.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The Entity represents service interval.
 *
 * @author Lukas Hajek, 359617@mail.muni.cz
 */
@Entity
public class ServiceInterval implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Unique users ID
     */
    @Id
    @GeneratedValue(generator = "service_interval_id_sequence")
    @GenericGenerator(name = "service_interval_id_sequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
        @Parameter(name = "sequence_name", value = "service_interval_id_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")})
    private Long id;
    /**
     * The required time interval between two service inspections (in days).
     */
    @Column(nullable = false)
    private int inspectionInterval;
    /**
     * The dates when vehicle was inspected.
     */
    
    @ElementCollection(targetClass=java.util.Date.class)
    @Fetch(FetchMode.JOIN)
    @Temporal(javax.persistence.TemporalType.DATE)
    @OrderBy()
    private List<Date> dated;
    /**
     * The related vehicle
     */
    // @Column(nullable = false)
    @ManyToOne()
    private Vehicle vehicle;
    /**
     * Description of service interval. E.g: wheel exchange
     */
    @Column(nullable = false)
    private String description;

    /**
     * @return Service interval ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id Service interval ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    public int getInspectionInterval() {
        return inspectionInterval;
    }

    /**
     * Sets time interval between two service inspections
     *
     * @param inspectionInterval Interval between two service inspections (in days)
     * @throws IllegalArgumentException If inspectionInterval is less or equals to 0
     */
    public void setInspectionInterval(int inspectionInterval) {
        if (inspectionInterval <= 0) {
            throw new IllegalArgumentException("inspectionInterval");
        }
        this.inspectionInterval = inspectionInterval;
    }

    /**
     * @return List of dates when vehicle was inspected.
     */
    public List<Date> getDated() {
        return dated;
    }

    public void setDated(List<Date> dated) {
        this.dated = dated;
    }

    /**
     * @return The connected vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @param vehicle The related vehicle
     * @throws IllegalArgumentException When the vehicle is null
     */
    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle");
        }
        this.vehicle = vehicle;
    }

    /**
     * @return Description of service interval.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description Description of service interval.
     * @throws IllegalArgumentException When description is null
     */
    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("description");
        }
        this.description = description;
    }

    @Override
    public String toString() {
        return "ServiceInterval{" + "id=" + id + ", inspectionInterval=" + inspectionInterval + ", dated=" + dated + ", vehicle=" + vehicle
                + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ServiceInterval)) {
            return false;
        }
        final ServiceInterval other = (ServiceInterval) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
