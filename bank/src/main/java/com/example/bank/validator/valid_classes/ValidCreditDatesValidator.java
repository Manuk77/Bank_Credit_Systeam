package com.example.bank.validator.valid_classes;


import com.example.bank.customer.creating_requests.requests.CreditRequest;
import com.example.bank.validator.annotation.ValidCreditDates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidCreditDatesValidator implements ConstraintValidator<ValidCreditDates, Object> {

    @Override
    public void initialize(ValidCreditDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object credit, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();

        if (credit instanceof CreditRequest creditRequest){
            LocalDate startCreditDate = LocalDate.parse(creditRequest.startCreditDate());
            LocalDate endCreditDate = LocalDate.parse(creditRequest.endCreditDate());
            return startCreditDate.isBefore(endCreditDate);
        }

        //        else if (passport instanceof CreditUpdateRequest creditUpdateRequest) {
//            LocalDate startCreditDate = LocalDate.parse(passportUpdateRequest.givenDate());
//            LocalDate endCreditDate = LocalDate.parse(passportUpdateRequest.expireDate());
//            return !givenDate.isAfter(today) && expireDate.isAfter(today);
//        }

        return false;
    }
}