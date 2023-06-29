package com.example.acra.customer.requests.creating_requests;

import com.example.acra.validation.annotations.NotNullEmptyBlankString;
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
