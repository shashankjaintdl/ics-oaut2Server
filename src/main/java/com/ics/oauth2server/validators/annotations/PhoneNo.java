package com.ics.oauth2server.validators.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD,ElementType.FIELD,ElementType.TYPE_USE,ElementType.PARAMETER,ElementType.CONSTRUCTOR,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface PhoneNo {
    String message() default "{com.ics.oauth2server.validators.annotations.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
