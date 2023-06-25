package com.example.bank.customer.response;

import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import com.example.bank.customer.dto.CustomerModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "customer_info")
public record CustomerResponse(

        @JsonProperty
        AddressResponse addressResponse,
        @JsonProperty
        PassportResponse passportResponse,
        @JsonProperty
        CustomerInfoResponse customerInfoResponse,
        @JsonProperty
        WorkingPlaceResponse workingPlaceResponse,
        @JsonProperty
        CustomerHistoryResponse customerHistoryResponse

) {
    public static CustomerResponse getFromModel(final CustomerModel customerModel){
        return new CustomerResponse(
                AddressResponse.getFromModel(customerModel.getAddressModel()),
                PassportResponse.getFromModel(customerModel.getPassportModel()),
                CustomerInfoResponse.getFromModel(customerModel.getCustomerInfoModel()),
                WorkingPlaceResponse.getFromModel(customerModel.getWorkingPlaceModel()),
                CustomerHistoryResponse.getFromModel(customerModel.getCustomerHistoryModel())
        );
    }

    public static CustomerResponse getFromRequest(final CustomerRequest customerRequest) {
        return new CustomerResponse(
                AddressResponse.getFromRequest(customerRequest.addressRequest()),
                PassportResponse.getFromRequest(customerRequest.passportRequest()),
                CustomerInfoResponse.getFromRequest(customerRequest.customerInfoRequest()),
                WorkingPlaceResponse.getFromRequest(customerRequest.workingPlaceRequest()),
                CustomerHistoryResponse.getFromRequest(customerRequest.customerHistoryRequest()));
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "addressResponse=" + addressResponse +
                ", passportResponse=" + passportResponse +
                ", customerInfoResponse=" + customerInfoResponse +
                ", workingPlaceResponse=" + workingPlaceResponse +
                ", customerHistoryResponse=" + customerHistoryResponse +
                '}';
    }
}