package cz.muni.fi.pa165.vozovna.dto;

import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import java.util.List;

/**
 * Data Transfer Object for  Vehicle
 * 
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
    private Integer maxDistance;
    
    /**
     * Type of engine
     */
    private String engineType;
    
    /**
     * If true, vehicle can be removed, because has not active rides.
     */
    private boolean canRemove;
    
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
    private Integer yearMade;
    
    /**
     * The class of user that can use this vehicle.
     */
    private UserClassEnum userClass;

    /**
     * Registration plate (licence plate / number plate / tag)
     */
    private String registrationPlate;
    
    /**
     * Sum of all km went by vehicle.
     */
    private int mileage;
    
    /**
     * Service intervals of vehicle
     */
    private List<ServiceIntervalDTO> serviceIntervals;
    
    // SETTER & GETTER METHODS:
    
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

    public Integer getDistanceCount() {
        return maxDistance;
    }

    public void setDistanceCount(Integer distanceCount) {
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
    
    public boolean getCanRemove() {
        return this.canRemove;
    }
    
    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public Integer getYearMade() {
        return yearMade;
    }

    public void setYearMade(Integer yearMade) {
        this.yearMade = yearMade;
    }

    public UserClassEnum getUserClass() {
        return userClass;
    }

    public void setUserClass(UserClassEnum userClass) {
        this.userClass = userClass;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }
    
    
    // CONSTRUCTORS:
    
    public VehicleDTO() {

    }
    
    /**
     * Creates VehicleDTO from Vehicle entity
     * @param vehicle   Original vehicle
     */
    public VehicleDTO(Vehicle vehicle) {
        fromVehicle(vehicle);
    }
    
    // CONVERTORS FROM AND TO ENTITY:
    
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
        registrationPlate = vehicle.getRegistrationPlate();
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
        vehicle.setRegistrationPlate(registrationPlate);
        
        return vehicle;
    }
    
    
    // OTHER METHODS:
    
    @Override
    public String toString() {
        return "VehicleDTO{" 
            + "id=" + id 
            + ", brand=" + brand 
            + ", maxDistance=" + maxDistance 
            + ", engineType=" + engineType 
            + ", type=" + type 
            + ", vin=" + vin 
            + ", yearMade=" + yearMade 
            + ", userClass=" + userClass 
            + ", registrationPlate=" + registrationPlate 
            + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getFullName() {
        return this.brand + " " + this.type + " (" + this.yearMade + ")";
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

    /**
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * @param mileage the mileage to set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * @return the serviceIntervals
     */
    public List<ServiceIntervalDTO> getServiceIntervals() {
        return serviceIntervals;
    }

    /**
     * @param serviceIntervals the serviceIntervals to set
     */
    public void setServiceIntervals(List<ServiceIntervalDTO> serviceIntervals) {
        this.serviceIntervals = serviceIntervals;
    }


    

   
}
