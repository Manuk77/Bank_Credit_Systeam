package com.example.acra.customer.response;

import com.example.acra.customer.dto.AddressModel;
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
    public static AddressResponse getFromModel(AddressModel addressModel){
        return new AddressResponse(
                addressModel.getStreet(),
                addressModel.getCity(),
                addressModel.getCountry());
    }
}
