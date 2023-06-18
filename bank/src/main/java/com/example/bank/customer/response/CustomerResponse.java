package com.example.bank.customer.response;

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
    public static CustomerResponse getFromModel(CustomerModel customerModel){
        return new CustomerResponse(
                AddressResponse.getFromModel(customerModel.getAddressModel()),
                PassportResponse.getFromModel(customerModel.getPassportModel()),
                CustomerInfoResponse.getFromModel(customerModel.getCustomerInfoModel()),
                WorkingPlaceResponse.getFromModel(customerModel.getWorkingPlaceModel()),
                CustomerHistoryResponse.getFromModel(customerModel.getCustomerHistoryModel())
        );
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