package cz.muni.fi.pa165.vozovna.editors;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import java.beans.PropertyEditorSupport;

/**
 * @author Eva Neduchalová, 359893
 */
public class VehicleDTOEditor extends PropertyEditorSupport {

    private VehicleService vehicleService;

    public VehicleDTOEditor(VehicleService vehicleService) {
        super();
        this.vehicleService = vehicleService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        VehicleDTO vehicleDTO = vehicleService.getById((long) Integer.parseInt(text));
        setValue(vehicleDTO);
    }

    @Override
    public String getAsText() {
        VehicleDTO vehicle = (VehicleDTO) getValue();
        if (vehicle == null) {
            return null;
        } else {
            return vehicle.getId().toString();
        }
    }
}
