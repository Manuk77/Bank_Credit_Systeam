package com.example.acra.customer.response;

import com.example.acra.customer.dto.CustomerModel;

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
                if (customerModel == null)
                        return null;
                return new CustomerResponse(
                        AddressResponse.getFromModel(customerModel.getAddressModel()),
                        PassportResponse.getFromModel(customerModel.getPassportModel()),
                        CustomerInfoResponse.getFromModel(customerModel.getCustomerInfoModel()),
                        WorkingPlaceResponse.getFromModel(customerModel.getWorkingPlaceModel()),
                        CustomerHistoryResponse.getFromModel(customerModel.getCustomerHistoryModel())
                );
        }
}
