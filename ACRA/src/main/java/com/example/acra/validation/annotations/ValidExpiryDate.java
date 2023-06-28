package com.example.acra.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = com.example.acra.validation.valid_classes.ValidExpiryDate.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpiryDate {
    String message() default "Invalid expiry date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
