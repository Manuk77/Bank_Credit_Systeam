package com.example.bank.validator.valid_classes;

import com.example.bank.customer.creating_requests.requests.PassportRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ValidExpiryDate implements ConstraintValidator<com.example.bank.validator.annotation.ValidExpiryDate, PassportRequest> {


    @Override
    public void initialize(com.example.bank.validator.annotation.ValidExpiryDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PassportRequest passportRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (passportRequest.issueDate() == null && passportRequest.expiryDate() == null) {
            return false;
        }

        assert passportRequest.issueDate() != null;
        LocalDate issueDate = LocalDate.parse(passportRequest.issueDate(), DateTimeFormatter.ISO_DATE);
        LocalDate expiryDate = LocalDate.parse(passportRequest.expiryDate(), DateTimeFormatter.ISO_DATE);

        Period period = Period.between(expiryDate, issueDate);
        int year = period.getYears();
        int month = period.getMonths();
        int day = period.getDays();
        return year == -10 && month == 0 && day == 0;
    }
}
