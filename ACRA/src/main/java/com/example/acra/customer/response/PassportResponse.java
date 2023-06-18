package com.example.acra.customer.response;

import com.example.acra.customer.dto.PassportModel;
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
}
