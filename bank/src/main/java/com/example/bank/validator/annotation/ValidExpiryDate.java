package com.example.bank.validator.annotation;

import com.example.bank.validator.valid_classes.ValidCreditDatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = com.example.bank.validator.valid_classes.ValidExpiryDate.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpiryDate {
    String message() default "Invalid expiry date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
