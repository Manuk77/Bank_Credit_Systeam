package com.example.bank.customer.creating_requests.requests;
import com.example.bank.customer.dto.WorkingPlaceModel;
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
        @Pattern(regexp = "^([6-9]\\d{4}|[1-9]\\d{5,})$")//"^([֏$€₽])?\\d+(\\.\\d+)?$"
        @JsonProperty("salary")
        String salary
) {
        public static WorkingPlaceRequest getFromResponse(final WorkingPlaceResponse workingPlaceResponse) {
                return new WorkingPlaceRequest(
                        workingPlaceResponse.name(),
                        workingPlaceResponse.salary());
        }

        public static WorkingPlaceRequest getFromModel(final WorkingPlaceModel workingPlaceModel) {
                return new WorkingPlaceRequest(
                        workingPlaceModel.getName(),
                        workingPlaceModel.getSalary()
                );
        }

        @Override
        public String toString() {
                return "WorkingPlaceRequest{" +
                        "name='" + name + '\'' +
                        ", salary='" + salary + '\'' +
                        '}';
        }
}