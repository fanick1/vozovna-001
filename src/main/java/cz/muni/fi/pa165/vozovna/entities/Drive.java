package cz.muni.fi.pa165.vozovna.entities;

import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import java.util.Date;
import javax.persistence.*;

/**
 * Jízda. Slouží pro rezervaci vozidel na určitý čas a pro záznam najetých
 * kilometrů.
 *
 * @author eva.neduchalova
 */
@Entity
@Table(name = "Drive")
//@TypeDefs({ @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
@SequenceGenerator(name = "drive_id_sequence", sequenceName = "drive_id_sequence", allocationSize = 1)
public class Drive {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "land_id_sequence")
    private Long id;
    
    private Integer distance;
    
    @OneToOne(targetEntity = VozovnaUser.class)
    @JoinColumn(name = "id_user")
    private VozovnaUser user;
    
    @OneToOne(targetEntity = Vehicle.class)
    @JoinColumn(name = "id_vehicle")
    private Vehicle vehicle;
    
    @Column(name = "date_from")
    //@Type(type = "jodaDateTime")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    
    @Column(name = "date_to")
    //@Type(type = "jodaDateTime")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    
    @Column(name = "state")
    private DriveStateEnum state;

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
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

    public VozovnaUser getUser() {
        return user;
    }

    public void setUser(VozovnaUser user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Drive{" + "id=" + id + ", distance=" + distance + ", user=" + user 
                + ", vehicle=" + vehicle + ", dateFrom=" + dateFrom + ", dateTo=" 
                + dateTo + ", state=" + state + '}';
    }
}
