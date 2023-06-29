package com.example.acra.customer.requests.creating_requests;

import com.example.acra.validation.annotations.ValidFirstNameAndLastName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
@ValidFirstNameAndLastName
public record CustomerRequest(
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
        @JsonProperty("credit_history")
        CustomerHistoryRequest customerHistoryRequest,
        @Valid
        @JsonProperty("working_place")
        WorkingPlaceRequest workingPlaceRequest) {
}
