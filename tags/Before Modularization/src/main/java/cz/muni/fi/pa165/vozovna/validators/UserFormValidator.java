package cz.muni.fi.pa165.vozovna.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;

public class UserFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.user.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.user.LastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.user.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.user.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userClass", "error.user.userClass");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isAdmin", "error.user.isAdmin");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enabled", "error.user.enabled");

    }

}
