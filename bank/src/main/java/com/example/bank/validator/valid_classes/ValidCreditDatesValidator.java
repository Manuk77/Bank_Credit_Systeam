package com.example.bank.validator.valid_classes;


import com.example.bank.customer.creating_requests.requests.CreditRequest;
import com.example.bank.validator.annotation.ValidCreditDates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * The `ValidCreditDatesValidator` class is a validator that checks if the credit dates provided in the `CreditRequest`
 * are valid, ensuring that the start date is before the end date.
 * It implements the `ConstraintValidator` interface for validating objects annotated with the `ValidCreditDates` annotation.
 */
public class ValidCreditDatesValidator implements ConstraintValidator<ValidCreditDates, Object> {

    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation The `ValidCreditDates` annotation instance.
     */
    @Override
    public void initialize(ValidCreditDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Validates the credit object to ensure that the credit dates are valid.
     *
     * @param credit  The credit object to be validated.
     * @param context The validation context.
     * @return `true` if the credit dates are valid (start date is before the end date), `false` otherwise.
     */
    @Override
    public boolean isValid(Object credit, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();

        if (credit instanceof CreditRequest creditRequest){
            LocalDate startCreditDate = LocalDate.parse(creditRequest.startCreditDate());
            LocalDate endCreditDate = LocalDate.parse(creditRequest.endCreditDate());
            return startCreditDate.isBefore(endCreditDate);
        }


        return false;
    }
}
