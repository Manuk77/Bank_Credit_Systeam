package com.example.bank.customer.creating_requests.requests;

import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerRequest(
        @NotNullEmptyBlankString
        @JsonProperty("address_info")
        AddressRequest addressRequest,

        @NotNullEmptyBlankString
        @JsonProperty("passport_info")
        PassportRequest passportRequest,

        @NotNullEmptyBlankString
        @JsonProperty("customer_info")
        CustomerInfoRequest customerInfoRequest,

        @NotNullEmptyBlankString
        @JsonProperty("credit_history")
        CustomerHistoryRequest customerHistoryRequest,
        @NotNullEmptyBlankString
        @JsonProperty("working_place")
        WorkingPlaceRequest workingPlaceRequest) {
}