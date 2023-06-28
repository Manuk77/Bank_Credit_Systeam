package com.example.acra.validation.annotations;

import com.example.acra.validation.valid_classes.ValidPassportDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPassportDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassportDates {

    String message() default "Invalid passport date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
