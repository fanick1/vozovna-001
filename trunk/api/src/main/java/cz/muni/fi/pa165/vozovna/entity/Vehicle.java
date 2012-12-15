package cz.muni.fi.pa165.vozovna.entity;

import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 
 * @author Jozef Triscik
 */
@Entity
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "vehicle_id_sequence")
    @GenericGenerator(name = "vehicle_id_sequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "vehicle_id_sequence"), @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1") })
    private Long id;

    @Column(nullable = false, length = 20)
    private String brand;

    @Column(nullable = true)
    private Integer maxDistance;

    @Column(nullable = false, length = 20)
    private String engineType;

    @Column(nullable = false, length = 40)
    private String type;

    @Column(nullable = false, length = 17)
    private String vin;

    @Column(nullable = true)
    private Integer yearMade;

    @Column(nullable = false)
    private UserClassEnum userClass;

    /**
     * Returns id of vehicle.
     * 
     * @return Id of vehicle.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id of vehicle.
     * 
     * @param id Id of vehicle.
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.id == null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.muni.fi.pa165.vozovna.entities.Vehicle[ id=" + id + " ]";
    }

    /**
     * Returns brand of vehicle.
     * 
     * @return Brand of vehicle
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets brand of vehicle.
     * 
     * @param brand Brand of vehicle.
     * @throws IllegalArgumentException Throws if given brand is null or empty.
     */
    public void setBrand(String brand) {
        if (brand == null || brand.isEmpty()) {
            throw new IllegalArgumentException("Given brand cannot be null or empty!");
        }
        this.brand = brand;
    }

    /**
     * Returns max distance, which can vehicle go.
     * 
     * @return Max distance, which can vehicle go.
     */
    public Integer getDistanceCount() {
        return maxDistance;
    }

    /**
     * Sets max distance, which can vehicle go.
     * 
     * @param distanceCount Max distance
     * @throws IllegalArgumentException Throws in case, that distance is less or equal to zero.
     */
    public void setDistanceCount(Integer distanceCount) {
        if (distanceCount != null && distanceCount < 0) {
            throw new IllegalArgumentException("Value of distanceCount can't be less than zero.");
        }
        this.maxDistance = distanceCount;
    }

    /**
     * Returns type of engine.
     * 
     * @return Type of engine.
     */
    public String getEngineType() {
        return engineType;
    }

    /**
     * Set given type of engine to vehicle.
     * 
     * @param engineType Type of engine of vehicle.
     * @throws IllegalArgumentException Throws in case, that given engine type is null or empty.
     */
    public void setEngineType(String engineType) {
        if (engineType == null || engineType.isEmpty()) {
            throw new IllegalArgumentException("Given engine type cannot be null or empty.");
        }
        this.engineType = engineType;
    }

    /**
     * Returns vehicle type.
     * 
     * @return Type of vehicle.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type of vehicle.
     * 
     * @param type Type of vehicle
     * @throws IllegalArgumentException Throws in case, that given type is null or empty.
     */
    public void setType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Given engine type cannot be null or empty.");
        }
        this.type = type;
    }

    /**
     * Returns vehicle identifier number.
     * 
     * @return Vehicle identifier number
     */
    public String getVin() {
        return vin;
    }

    /**
     * Sets vehicle identifier number.
     * 
     * @param vin Vehicle identifier number
     * @throws IllegalArgumentException Throws in case, that given VIN is null or empty.
     */
    public void setVin(String vin) {
        if (vin == null || vin.isEmpty()) {
            throw new IllegalArgumentException("Given VIN cannot be null or empty.");
        }
        this.vin = vin;
    }

    /**
     * Returns year, when the vehicle was made.
     * 
     * @return
     */
    public Integer getYearMade() {
        return yearMade;
    }

    /**
     * Sets year, when the vehicle was made.
     * 
     * @param yearMade Year of made.
     * @throws IllegalArgumentException Throws if year is less than 1900.
     */
    public void setYearMade(Integer yearMade) {
        if (yearMade != null && yearMade < 1900) {
            throw new IllegalArgumentException("Value of year must be higher than 1900");
        }
        this.yearMade = yearMade;
    }

    /**
     * Returns user class of vehicle.
     * 
     * @return the user class
     */
    public UserClassEnum getUserClass() {
        return userClass;
    }

    /**
     * Sets user class of vehicle.
     * 
     * @param userClass the userClass to set
     */
    public void setUserClass(UserClassEnum userClass) {
        this.userClass = userClass;
    }
}
