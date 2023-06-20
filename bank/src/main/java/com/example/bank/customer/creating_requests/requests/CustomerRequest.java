package com.example.bank.customer.creating_requests.requests;

import com.example.bank.customer.response.CustomerResponse;
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

        public static CustomerRequest getFromResponse(final CustomerResponse customerResponse) {
                return new CustomerRequest(
                        AddressRequest.getFromResponse(customerResponse.addressResponse()),
                        PassportRequest.getFromResponse(customerResponse.passportResponse()),
                        CustomerInfoRequest.getFromResponse(customerResponse.customerInfoResponse()),
                        CustomerHistoryRequest.getFromResponse(customerResponse.customerHistoryResponse()),
                        WorkingPlaceRequest.getFromResponse(customerResponse.workingPlaceResponse())
                );
        }
}