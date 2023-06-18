package com.example.acra.customer.requests.creating_requests;

import com.example.acra.annotation.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record WorkingPlaceRequest(

        @NotNullEmptyBlankString
        @Pattern(regexp = "^[A-Za-z0-9\\s,.-]*$")
        @JsonProperty("name")
        String name,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^([֏$€₽])?\\d+(\\.\\d+)?$")
        @JsonProperty("salary")
        String salary
) {
}
