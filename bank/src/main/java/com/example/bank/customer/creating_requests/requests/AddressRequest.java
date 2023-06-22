package com.example.bank.customer.creating_requests.requests;


import com.example.bank.customer.dto.AddressModel;
import com.example.bank.customer.response.AddressResponse;
import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record AddressRequest(
        @NotNullEmptyBlankString
        @Pattern(regexp = "^[A-Za-z0-9\\s,.-]*$")
        @JsonProperty("street")
        String street,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^[A-Za-z\\s.-]*$")
        @JsonProperty("city")
        String city,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^[A-Za-z\\s',.-]*$")
        @JsonProperty("country")
        String country) {

        public static AddressRequest getFromResponse(final AddressResponse addressResponse) {
                return new AddressRequest(
                        addressResponse.street(),
                        addressResponse.city(),
                        addressResponse.country());
        }

        public static AddressRequest getFromModel(final AddressModel addressModel) {
                return new AddressRequest(
                        addressModel.getStreet(),
                        addressModel.getCity(),
                        addressModel.getCountry()
                );
        }

        @Override
        public String toString() {
                return "AddressRequest{" +
                        "street='" + street + '\'' +
                        ", city='" + city + '\'' +
                        ", country='" + country + '\'' +
                        '}';
        }
}
