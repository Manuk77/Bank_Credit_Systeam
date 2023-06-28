package com.example.acra.validation.annotations;

import com.example.acra.validation.valid_classes.ValidCreditDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = ValidCreditDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCreditDates {
    String message() default "Invalid credit date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
