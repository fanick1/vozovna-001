package cz.muni.fi.pa165.vozovna.validators;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class DriveFinishedValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {
        return DriveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "distance", "error.drive.distance");
        Drive drive = (Drive) obj;
        
        // distance
        if (drive.getDistance() != null && drive.getDistance() < 0) {
            errors.rejectValue("distance", "error.drive.distance.ltZero");
        }


    }

}
