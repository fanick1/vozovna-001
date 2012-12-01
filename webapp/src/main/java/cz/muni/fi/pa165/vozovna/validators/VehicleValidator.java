package cz.muni.fi.pa165.vozovna.validators;

import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class VehicleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return VehicleDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "error.vehicle.brand");
        ValidationUtils.rejectIfEmpty(errors, "distanceCount", "error.vehicle.distanceCount");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "engineType", "error.vehicle.engineType");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "error.vehicle.type");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vin", "error.vehicle.vin");
        ValidationUtils.rejectIfEmpty(errors, "yearMade", "error.vehicle.yearMade");
        ValidationUtils.rejectIfEmpty(errors, "userClass", "error.vehicle.userClass");

        VehicleDTO vehicle = (VehicleDTO) obj;

        // validate yearMade
        if (vehicle.getYearMade() < 1900) {
            errors.rejectValue("yearMade", "error.vehicle.yearMade.min");
        }
        // validate yearMade
        if (vehicle.getYearMade() > 2100) {
            errors.rejectValue("yearMade", "error.vehicle.yearMade.max");
        }
        // validate yearMade
        if (vehicle.getDistanceCount() < 0) {
            errors.rejectValue("distanceCount", "error.vehicle.distanceCount.lsThanZero");
        }

        // brand
        if (vehicle.getBrand().length() > 20) {
            errors.rejectValue("brand", "error.vehicle.brand.maxLength");
        }
        // type
        if (vehicle.getType().length() > 40) {
            errors.rejectValue("type", "error.vehicle.type.maxLength");
        }
        // engineType
        if (vehicle.getEngineType().length() > 20) {
            errors.rejectValue("engineType", "error.vehicle.engineType.maxLength");
        }

        // vin
        if (vehicle.getVin().length() > 17) {
            errors.rejectValue("vin", "error.vehicle.vin.maxLength");
        }

    }
}
