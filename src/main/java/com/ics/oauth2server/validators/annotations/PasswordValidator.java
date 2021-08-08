package com.ics.oauth2server.validators.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public  class PasswordValidator implements ConstraintValidator<Password, String> {

    private String regex;


    @Override
    public void initialize(Password constraintAnnotation) {
        this.regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)  {
        if (!(value != null && patternMatches(regex,value)
                && (value.length() >= 8) && (value.length() <= 14))){
            return false;
        }
        return true;

    }

    private  Boolean patternMatches(String pattern,String value){
        Pattern r = Pattern.compile(pattern);
        return r.matcher(value).matches();
    }
    //        if(patternMatches("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+",value)){
    //            message = "Password at least must contains Characters and numbers";
    //            return false;
    //        }
    //    }
}

