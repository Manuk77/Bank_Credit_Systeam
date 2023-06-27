package com.example.bank.validator.valid_classes;
import com.example.bank.customer.creating_requests.requests.PassportRequest;
import com.example.bank.validator.annotation.ValidPassportDates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.time.LocalDate;

/**
 * The `ValidPassportDatesValidator` class is a validator that checks if the passport dates provided in the `PassportRequest`
 * are valid, ensuring that the issue date is before the expiry date and within the valid range.
 * It implements the `ConstraintValidator` interface for validating objects annotated with the `ValidPassportDates` annotation.
 */
public class ValidPassportDatesValidator implements ConstraintValidator<ValidPassportDates, Object> {

    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation The `ValidPassportDates` annotation instance.
     */
    @Override
    public void initialize(ValidPassportDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Validates the passport object to ensure that the passport dates are valid.
     *
     * @param passport The passport object to be validated.
     * @param context  The validation context.
     * @return `true` if the passport dates are valid (issue date is before the expiry date and within the valid range), `false` otherwise.
     */
    @Override
    public boolean isValid(Object passport, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();

        if (passport instanceof PassportRequest passportRequest) {
            LocalDate issueDate = LocalDate.parse(passportRequest.issueDate());
            LocalDate expiryDate = LocalDate.parse(passportRequest.expiryDate());
            return !issueDate.isAfter(today) && expiryDate.isAfter(today)
                    && issueDate.isBefore(expiryDate);
        }
        return false;
    }

}