package com.example.acra.validation.valid_classes;

import com.example.acra.customer.requests.creating_requests.CustomerRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFirstNameAndLastName implements ConstraintValidator<com.example.acra.validation.annotations.ValidFirstNameAndLastName, CustomerRequest> {
    @Override
    public void initialize(com.example.acra.validation.annotations.ValidFirstNameAndLastName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CustomerRequest customerRequest, ConstraintValidatorContext constraintValidatorContext) {
        if(customerRequest == null){
            return false;
        }
        return customerRequest.passportRequest().firstName().equals(customerRequest.customerInfoRequest().firstName())
                && customerRequest.passportRequest().lastName().equals(customerRequest.customerInfoRequest().lastName());
    }
}
