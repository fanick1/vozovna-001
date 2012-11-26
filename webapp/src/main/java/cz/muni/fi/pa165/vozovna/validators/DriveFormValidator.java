package cz.muni.fi.pa165.vozovna.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;

/**
 * @author Eva Neduchalová, 359893
 */
public class DriveFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DriveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateFrom", "error.drive.dateFrom.missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateTo", "error.drive.dateTo.missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "error.drive.state.missing");

        DriveDTO drive = (DriveDTO) target;

        if (drive.getDateFrom() != null && drive.getDateTo() != null && drive.getDateFrom().isAfter(drive.getDateTo())) {
            errors.rejectValue("dateTo", "error.drive.dateTo.soon");
        }

        if (drive.getUser() == null && drive.getUserId() == null) {
            errors.reject("error.drive.user.missing");
        }

        if (drive.getVehicle() == null && drive.getVehicleId() == null) {
            errors.reject("error.drive.vehicle.missing");
        }

    }

}
