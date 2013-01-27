package cz.muni.fi.pa165.vozovna.entity;

import cz.muni.fi.pa165.vozovna.enums.DriveStateEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Drive. Used both for vehicle reservation and drive reports.
 * 
 * @author eva.neduchalova
 */
@Entity
@Table(name = "Drives")
public class Drive {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "drive_id_sequence")
    @GenericGenerator(name = "drive_id_sequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "drive_id_sequence"), @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1") })
    /**
     * Unique id
     */
    private Long id;
    @Column(name = "distance")
    /**
     * Kilometres traveled in this drive
     */
    private Integer distance;
    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "id_user")
    /**
     * User, to whom the vehicle is being lent for this drive
     */
    private User user;
    @OneToOne(targetEntity = Vehicle.class)
    @JoinColumn(name = "id_vehicle")
    /**
     * Vehicle which is lent for this drive
     */
    private Vehicle vehicle;
    @Column(name = "date_from")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    /**
     * Date when the Drive started
     */
    private DateTime dateFrom;
    @Column(name = "date_to")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    /**
     * Date when the drive ended
     */
    private DateTime dateTo;
    @Column(name = "drive_state")
    /**
     * State of this Drive. (Before drive, drive ongoing, drive cancelled, drive finished)
     */
    // TODO nastavit mapovani do DB na string
    private DriveStateEnum state;

    public Drive() {
        super();
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
        return "Drive{" + "id=" + id + ", distance=" + distance + ", user=" + user + ", vehicle=" + vehicle + ", dateFrom=" + dateFrom
                + ", dateTo=" + dateTo + ", state=" + state + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Drive)) {
            return false;
        }
        final Drive other = (Drive) obj;
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
}
