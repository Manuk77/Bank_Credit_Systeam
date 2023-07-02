package com.example.bank.customer.creating_requests.requests;

import com.example.bank.customer.dto.CustomerModel;
import com.example.bank.customer.response.CustomerResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;

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

        public static CustomerRequest getFromResponse(final CustomerResponse customerResponse) {
                return new CustomerRequest(
                        AddressRequest.getFromResponse(customerResponse.addressResponse()),
                        PassportRequest.getFromResponse(customerResponse.passportResponse()),
                        CustomerInfoRequest.getFromResponse(customerResponse.customerInfoResponse()),
                        CustomerHistoryRequest.getFromResponse(customerResponse.customerHistoryResponse()),
                        WorkingPlaceRequest.getFromResponse(customerResponse.workingPlaceResponse())
                );
        }

        public static CustomerRequest getFromModel(final CustomerModel customerModel) {
                return new CustomerRequest(
                        AddressRequest.getFromModel(customerModel.getAddressModel()),
                        PassportRequest.getFromModel(customerModel.getPassportModel()),
                        CustomerInfoRequest.getFromModel(customerModel.getCustomerInfoModel()),
                        CustomerHistoryRequest.getFromModel(customerModel.getCustomerHistoryModel()),
                        WorkingPlaceRequest.getFromModel(customerModel.getWorkingPlaceModel())
                );
        }

        @Override
        public String toString() {
                return "CustomerRequest{" +
                        "addressRequest=" + addressRequest +
                        ", passportRequest=" + passportRequest +
                        ", customerInfoRequest=" + customerInfoRequest +
                        ", customerHistoryRequest=" + customerHistoryRequest +
                        ", workingPlaceRequest=" + workingPlaceRequest +
                        '}';
        }
}