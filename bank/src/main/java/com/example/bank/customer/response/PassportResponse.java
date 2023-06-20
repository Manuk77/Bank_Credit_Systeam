package com.example.bank.customer.response;

import com.example.bank.customer.creating_requests.requests.PassportRequest;
import com.example.bank.customer.dto.PassportModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Pattern;
@JsonRootName(value = "passport")
public record PassportResponse(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("dob")
        String birthDate,
        @JsonProperty("gender")
        String gender,

        @Pattern(
                regexp = "[A-Z]{2}\\d{7}",
                message = "'passport number' must have 2 uppercase letters followed by 7 digits"
        )
        @JsonProperty("passport_number")
        String passportNumber,
        @JsonProperty("issue_date")
        String issueDate,
        @JsonProperty("expiry_date")
        String expiryDate,
        @JsonProperty("authority")
        String authority

) {
    public static PassportResponse getFromModel(PassportModel passportModel){
        return new PassportResponse(
                passportModel.getFirstName(),
                passportModel.getLastName(),
                passportModel.getBirthDate().toString(),
                passportModel.getGender(),
                passportModel.getPassportNumber(),
                passportModel.getIssueDate().toString(),
                passportModel.getExpiryDate().toString(),
                passportModel.getAuthority()
        );
    }

    public static PassportResponse getFromRequest(final PassportRequest passportRequest) {
       return new PassportResponse( passportRequest.firstName(),
                passportRequest.lastName(),
                passportRequest.birthDate(),
                passportRequest.gender(),
                passportRequest.passportNumber(),
                passportRequest.issueDate(),
                passportRequest.expiryDate(),
                passportRequest.authority());
    }

    @Override
    public String toString() {
        return "PassportResponse{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}