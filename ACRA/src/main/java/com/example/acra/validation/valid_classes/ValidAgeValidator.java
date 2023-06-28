package com.example.acra.validation.valid_classes;


import com.example.acra.customer.requests.creating_requests.CustomerInfoRequest;
import com.example.acra.validation.annotations.ValidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * The `ValidAgeValidator` class is a validator that checks if the age provided in the `CustomerInfoRequest`
 * matches the calculated age based on the birth date.
 * It implements the `ConstraintValidator` interface for validating the `CustomerInfoRequest` objects
 * annotated with the `ValidAge` annotation.
 */
public class ValidAgeValidator implements ConstraintValidator<ValidAge, CustomerInfoRequest> {

    /**
     * Validates the `CustomerInfoRequest` object to ensure that the age matches the calculated age based on the birth date.
     *
     * @param request The `CustomerInfoRequest` object to be validated.
     * @param context The validation context.
     * @return `true` if the age is valid and matches the calculated age, `false` otherwise.
     */
    @Override
    public boolean isValid(CustomerInfoRequest request, ConstraintValidatorContext context) {

        if (request.age() == null || request.birthDate() == null) {
            return false;
        }

        int age = Integer.parseInt(request.age());
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(request.birthDate(), DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            return false;
        }
        LocalDate currentYear = LocalDate.now();
        Period period = Period.between(birthDate, currentYear);
        int birthYear = period.getYears();

        return age == birthYear;
    }
}

