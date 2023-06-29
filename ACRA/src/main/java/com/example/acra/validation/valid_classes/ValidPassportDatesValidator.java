package com.example.acra.validation.valid_classes;

import com.example.acra.validation.annotations.ValidPassportDates;
import com.example.acra.customer.requests.creating_requests.PassportRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidPassportDatesValidator implements ConstraintValidator<ValidPassportDates, Object> {

    @Override
    public void initialize(ValidPassportDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object passport, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();

        if (passport instanceof PassportRequest passportRequest) {
            LocalDate issueDate = LocalDate.parse(passportRequest.issueDate());
            LocalDate expiryDate = LocalDate.parse(passportRequest.expiryDate());
            return !issueDate.isAfter(today) && expiryDate.isAfter(today)
                    && issueDate.isBefore(expiryDate);
        }
//        else if (passport instanceof PassportUpdateRequest passportUpdateRequest) {
//            LocalDate givenDate = LocalDate.parse(passportUpdateRequest.givenDate());
//            LocalDate expireDate = LocalDate.parse(passportUpdateRequest.expireDate());
//            return !givenDate.isAfter(today) && expireDate.isAfter(today);
//        }
        return false;
    }

}