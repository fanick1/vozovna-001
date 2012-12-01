package cz.muni.fi.pa165.vozovna.editors;

import java.beans.PropertyEditorSupport;

import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.service.ServiceIntervalService;

/**
 * @author Eva Neduchalová, 359893
 */
public class ServiceIntervalEditor extends PropertyEditorSupport {

    private ServiceIntervalService serviceIntervalService;

    public ServiceIntervalEditor(ServiceIntervalService serviceIntervalService) {
        super();
        this.serviceIntervalService = serviceIntervalService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ServiceIntervalDTO serviceInterval = serviceIntervalService.getById((long) Integer.parseInt(text));
        setValue(serviceInterval);
    }

    @Override
    public String getAsText() {
        ServiceIntervalDTO serviceInterval = (ServiceIntervalDTO) getValue();
        if (serviceInterval == null) {
            return null;
        } else {
            return serviceInterval.getId().toString();
        }
    }
}
