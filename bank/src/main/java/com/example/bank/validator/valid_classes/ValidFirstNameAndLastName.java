package com.example.bank.validator.valid_classes;

import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFirstNameAndLastName implements ConstraintValidator<com.example.bank.validator.annotation.ValidFirstNameAndLastName, CustomerRequestFiltered> {
    @Override
    public void initialize(com.example.bank.validator.annotation.ValidFirstNameAndLastName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CustomerRequestFiltered customerRequestFiltered, ConstraintValidatorContext constraintValidatorContext) {
        if(customerRequestFiltered == null){
            return false;
        }
        return customerRequestFiltered.passportRequest().firstName().equals(customerRequestFiltered.customerInfoRequest().firstName())
                && customerRequestFiltered.passportRequest().lastName().equals(customerRequestFiltered.customerInfoRequest().lastName());
    }
}
