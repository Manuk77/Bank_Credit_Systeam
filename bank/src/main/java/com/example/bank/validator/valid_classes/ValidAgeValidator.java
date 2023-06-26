package com.example.bank.validator.valid_classes;

import com.example.bank.customer.creating_requests.requests.CustomerInfoRequest;
import com.example.bank.validator.annotation.ValidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidAgeValidator implements ConstraintValidator<ValidAge, CustomerInfoRequest> {
    @Override
    public boolean isValid(CustomerInfoRequest request, ConstraintValidatorContext context) {
        if (request.age() == null || request.birthDate() == null) {
            return false;
        }

        int age = Integer.parseInt(request.age());

        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(request.birthDate(), DateTimeFormatter.ISO_DATE);
            System.out.println(birthDate);
        } catch (Exception e) {
            return false;
        }
        LocalDate currentYear = LocalDate.now();
        System.out.println(currentYear);
        Period period = Period.between(birthDate,currentYear);
        System.out.println(period.getYears());

        int birthYear = period.getYears();


        return age == birthYear;
    }
}

