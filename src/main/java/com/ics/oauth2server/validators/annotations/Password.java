package com.ics.oauth2server.validators.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD,ElementType.FIELD,ElementType.TYPE_USE,ElementType.PARAMETER,ElementType.CONSTRUCTOR,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "{com.ics.oauth2server.validators.annotations.message";
    // Regex should be match at-least 3 char 1, Upper 1 char, 1 Special Char, 1 number
    String regex() default "\\A(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\\z";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
