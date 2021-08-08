package com.ics.oauth2server.validators.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNoValidator implements ConstraintValidator<PhoneNo,String> {

    @Override
    public void initialize(PhoneNo constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(! (value.length() >= 10 && value.length() <= 12)){
            return false;
        }
        return true;
    }
}
