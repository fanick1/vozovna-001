package cz.muni.fi.pa165.vozovna.dto;

import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * Data Transfer Object for  Vehicle
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class VehicleDTO implements java.io.Serializable {
 
    /**
     * Identification key
     */
    private Long id;
    
    /**
     * Brand of vehicle
     */
    private String brand;
    
    /**
     * Max distance, which can vehicle go in kilometres.
     */
    private int maxDistance;
    
    /**
     * Type of engine
     */
    private String engineType;
    
    /**
     * Vehicle type
     */
    private String type;
    
    /**
     * Vehicle Identifier Number.
     */
    private String vin;
    
    /**
     * The year when was vehicle made.
     */
    private int yearMade;
    
    /**
     * The class of user that can use this vehicle.
     */
    private UserClassEnum userClass;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getDistanceCount() {
        return maxDistance;
    }

    public void setDistanceCount(int distanceCount) {
        this.maxDistance = distanceCount;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getYearMade() {
        return yearMade;
    }

    public void setYearMade(int yearMade) {
        this.yearMade = yearMade;
    }

    public UserClassEnum getUserClass() {
        return userClass;
    }

    public void setUserClass(UserClassEnum userClass) {
        this.userClass = userClass;
    }
    
    public VehicleDTO() {

    }
    
    public VehicleDTO(Vehicle vehicle) {
        fromVehicle(vehicle);
    }
    
    /**
     * Fills properties from vehicle
     * @param vehicle   Original vehicle
     */
    public final void fromVehicle(Vehicle vehicle) {
        id = vehicle.getId();
        brand = vehicle.getBrand();
        maxDistance = vehicle.getDistanceCount();
        engineType = vehicle.getEngineType();
        type = vehicle.getType();
        vin = vehicle.getVin();
        yearMade = vehicle.getYearMade();
        userClass = vehicle.getUserClass();  
    }
    
    /**
     * Returns vehicle with same properties
     * @return  Instance of vehicle
     */
    public Vehicle toVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setBrand(brand);
        vehicle.setDistanceCount(maxDistance);
        vehicle.setEngineType(engineType);
        vehicle.setType(type);
        vehicle.setVin(vin);
        vehicle.setYearMade(yearMade);
        vehicle.setUserClass(userClass);

        return vehicle;
    }
    
    @Override
    public String toString() {
        return "VehicleDTO{" + "id=" + id + ", brand=" + brand + ", maxDistance=" + maxDistance + ", engineType=" + engineType + ", type=" + type + ", vin=" + vin + ", yearMade=" + yearMade + ", userClass=" + userClass + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof VehicleDTO)) {
            return false;
        }
        VehicleDTO other = (VehicleDTO) object;
        if ((this.id == null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    

   
}
