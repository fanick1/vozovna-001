package cz.muni.fi.pa165.vozovna.dto;

import org.joda.time.DateTime;

import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;

/**
 * DTO of Drive
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class DriveDTO implements java.io.Serializable {

    /**
     * Unique id
     */
    private Long id;

    /**
     * Kilometres traveled in this drive
     */
    private Integer distance;

    /**
     * User, to whom the vehicle is being lent for this drive
     */
    private UserDTO user;

    /**
     * Vehicle which is lent for this drive
     */
    private VehicleDTO vehicle;

    /**
     * Date when the Drive started
     */
    private DateTime dateFrom;

    /**
     * Date when the drive ended
     */
    private DateTime dateTo;

    private Long userId;
    private Long vehicleId;

    /**
     * State of this Drive. (Before drive, drive ongoing, drive cancelled, drive finished)
     */
    private DriveStateEnum state;

    public DateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public DateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(DateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DriveStateEnum getState() {
        return state;
    }

    public void setState(DriveStateEnum state) {
        this.state = state;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DriveDTO)) {
            return false;
        }
        final DriveDTO other = (DriveDTO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DriveDTO [\nid=" + id + "\ndistance=" + distance + "\ndateFrom=" + dateFrom + "\ndateTo=" + dateTo + "\nuserId=" + userId
                + "\nvehicleId=" + vehicleId + "\nstate=" + state + "\n]";
    }

}
