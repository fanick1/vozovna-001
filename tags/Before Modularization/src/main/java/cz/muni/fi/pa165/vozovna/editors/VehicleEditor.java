package cz.muni.fi.pa165.vozovna.editors;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Lukas Hajek <359617@mail.muni.cz>
 */



public class VehicleEditor extends PropertyEditorSupport {

    private VehicleService vehicleService;

//    @Autowired
//    public void setVehicleService(VehicleService vehicleService) {
//        this.vehicleService = vehicleService;
//    }

    public VehicleEditor(VehicleService vehicleService) {
        super();
        this.vehicleService = vehicleService;
    }

    

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        VehicleDTO vehicleDTO = vehicleService.getById((long) Integer.parseInt(text));
        setValue(vehicleDTO.toVehicle());
    }

    @Override
    public String getAsText() {
        Vehicle vehicle = (Vehicle) getValue();
        if (vehicle == null) {
            return null;
        } else {
            return vehicle.getId().toString();
        }
    }

}