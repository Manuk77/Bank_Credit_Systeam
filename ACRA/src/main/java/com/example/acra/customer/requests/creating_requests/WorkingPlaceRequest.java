package com.example.acra.customer.requests.creating_requests;

import com.example.acra.validation.annotations.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record WorkingPlaceRequest(

        @NotNullEmptyBlankString
        @Pattern(regexp = "^[A-Za-z0-9\\s,.-]*$")
        @JsonProperty("name")
        String name,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^([6-9]\\d{4}|[1-9]\\d{5,})$")  //"^([֏$€₽])?\\d+(\\.\\d+)?$"
        @JsonProperty("salary")
        String salary
) {
}
