package com.example.bank.customer.response;

import com.example.bank.customer.creating_requests.requests.AddressRequest;
import com.example.bank.customer.dto.AddressModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "address")
public record AddressResponse(@JsonProperty("street")
                              String street,

                              @JsonProperty("city")
                              String city,

                              @JsonProperty("country")
                              String country
) {
    public static AddressResponse getFromModel(final AddressModel addressModel){
        return new AddressResponse(
                addressModel.getStreet(),
                addressModel.getCity(),
                addressModel.getCountry());
    }

    public static AddressResponse getFromRequest(final AddressRequest addressRequest) {
        return new AddressResponse(
                addressRequest.street(),
                addressRequest.city(),
                addressRequest.country()
        );
    }

    @Override
    public String toString() {
        return "AddressResponse{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
