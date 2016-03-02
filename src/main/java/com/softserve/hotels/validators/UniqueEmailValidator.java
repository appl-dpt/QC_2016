package com.softserve.hotels.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.softserve.hotels.annotations.UniqueEmail;
import com.softserve.hotels.service.UserService;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // need override validator with empty method
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (userService == null) {
            return true;
        }
        return userService.findUserByEmail(value) == null;
    }

}
