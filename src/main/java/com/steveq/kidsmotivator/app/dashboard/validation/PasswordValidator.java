package com.steveq.kidsmotivator.app.dashboard.validation;

import com.steveq.kidsmotivator.app.auth.model.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, Object> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // intentionally blank
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Password password = (Password) o;
        System.out.println("PASSWORD VALIDATION :: " + password.getPassword().equals(password.getConfirmPassword()));
        return password.getPassword().equals(password.getConfirmPassword());
    }
}
