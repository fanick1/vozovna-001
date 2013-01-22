package cz.muni.fi.pa165.vozovna.editors;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class VehicleEditor extends PropertyEditorSupport {

    private VehicleService vehicleService;

    public VehicleEditor(VehicleService vehicleService) {
        super();
        this.vehicleService = vehicleService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        VehicleDTO vehicleDTO = vehicleService.getById((long) Integer.parseInt(text));
        setValue(new Vehicle(vehicleDTO));
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