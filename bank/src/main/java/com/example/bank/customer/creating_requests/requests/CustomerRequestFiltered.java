package com.example.bank.customer.creating_requests.requests;


import com.example.bank.validator.annotation.ValidFirstNameAndLastName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
@ValidFirstNameAndLastName(message = "invalid first name or last name")
public record CustomerRequestFiltered(
        @Valid
        @JsonProperty("address_info")
        AddressRequest addressRequest,
        @Valid
        @JsonProperty("passport_info")
        PassportRequest passportRequest,
        @Valid
        @JsonProperty("customer_info")
        CustomerInfoRequest customerInfoRequest,
        @Valid
        @JsonProperty("new_credit_request")
        NewCreditRequest creditRequest,
        @Valid
        @JsonProperty("working_place")
        WorkingPlaceRequest workingPlaceRequest) {
}

