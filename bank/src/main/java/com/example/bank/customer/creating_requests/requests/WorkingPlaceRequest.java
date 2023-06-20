package com.example.bank.customer.creating_requests.requests;
import com.example.bank.customer.response.WorkingPlaceResponse;
import com.example.bank.validator.annotation.NotNullEmptyBlankString;
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
        public static WorkingPlaceRequest getFromResponse(final WorkingPlaceResponse workingPlaceResponse) {
                return new WorkingPlaceRequest(workingPlaceResponse.name(),
                        workingPlaceResponse.salary());
        }


}