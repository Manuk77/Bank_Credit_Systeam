package com.example.bank.customer.creating_requests.requests;


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

}
