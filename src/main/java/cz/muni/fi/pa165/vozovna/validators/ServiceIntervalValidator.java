package cz.muni.fi.pa165.vozovna.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;

/**
 * @author Eva Neduchalová, 359893
 */
public class ServiceIntervalValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DriveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inspectionInterval", "error.interval.inspectionInterval.missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.interval.description.missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicle", "error.interval.vehicle.missing");

        // ServiceIntervalDTO interval = (ServiceIntervalDTO) target;

    }

}
