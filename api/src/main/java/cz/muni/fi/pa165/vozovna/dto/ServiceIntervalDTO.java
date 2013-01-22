package cz.muni.fi.pa165.vozovna.dto;

import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for Service Interval
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class ServiceIntervalDTO implements java.io.Serializable {
    
    /**
     * Identification key
     */
    private Long id;
    
    /**
     * The required time interval between two service inspections (in days).
     */
    private int inspectionInterval;
    
    /**
     * The dates when vehicle was inspected.
     */
    private List<Date> dated;
    
    /**
     * The related vehicle
     */
    private VehicleDTO vehicle;
    
    /**
     * Description of service interval. E.g: wheel exchange
     */
    private String description;

    /**
     * If true, inspection id required for this service interval.
     */
    private boolean hasRequiredInspection;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getInspectionInterval() {
        return inspectionInterval;
    }

    public void setInspectionInterval(int inspectionInterval) {
        this.inspectionInterval = inspectionInterval;
    }

    public List<Date> getDated() {
        return dated;
    }

    public void setDated(List<Date> dated) {
        this.dated = dated;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
         this.vehicle = vehicle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceIntervalDTO() {

    }
    
    public ServiceIntervalDTO(ServiceInterval serviceInterval) {
        fromServiceInterval(serviceInterval);
    }
    
    /**
     * Fills properties from serviceInterval
     * @param serviceInterval Original Service Interval
     */
    public final void fromServiceInterval(ServiceInterval serviceInterval) {
        id = serviceInterval.getId();
        inspectionInterval = serviceInterval.getInspectionInterval();
        dated = serviceInterval.getDated();
        description = serviceInterval.getDescription();
        
        // vehicle
        VehicleDTO vehicleDTO = new VehicleDTO();
        Vehicle v = serviceInterval.getVehicle();
        if (v != null) {
            vehicleDTO.fromVehicle(serviceInterval.getVehicle());
            vehicle = vehicleDTO;
        }
    }
    
    /**
     * Returns service interval with same properties
     * @return Instance of Service Interval
     */
    public ServiceInterval toServiceInterval() {
        ServiceInterval serviceInterval = new ServiceInterval();
        serviceInterval.setId(id);
        serviceInterval.setInspectionInterval(inspectionInterval);
        serviceInterval.setDated(dated);
        serviceInterval.setVehicle(vehicle.toVehicle());
        serviceInterval.setDescription(description);
        
        return serviceInterval;
    }

    
    @Override
    public String toString() {
        return "ServiceIntervalDTO{" + "id=" + id + ", inspectionInterval=" + inspectionInterval + ", dated=" + dated + ", vehicle=" + vehicle + ", description=" + description + '}';
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
        if (!(obj instanceof ServiceIntervalDTO)) {
            return false;
        }
        final ServiceIntervalDTO other = (ServiceIntervalDTO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
      
    }

    /**
     * @return the hasRequiredInspection
     */
    public boolean isHasRequiredInspection() {
        return hasRequiredInspection;
    }

    /**
     * @param hasRequiredInspection the hasRequiredInspection to set
     */
    public void setHasRequiredInspection(boolean hasRequiredInspection) {
        this.hasRequiredInspection = hasRequiredInspection;
    }
    
}
