package com.example.bank.customer.creating_requests.requests;

import com.example.bank.customer.dto.PassportModel;
import com.example.bank.customer.response.PassportResponse;
import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.example.bank.validator.annotation.ValidExpiryDate;
import com.example.bank.validator.annotation.ValidPassportDates;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;



@ValidPassportDates (message = "Invalid issue/expiry date for PassportUpdateRequest: ")
@ValidExpiryDate
public record PassportRequest(

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "^[A-Z][A-Za-z\\s-]*$",
                message = "First name must start with uppercase"
        )
        @JsonProperty("first_name")
        String firstName,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "^[A-Z][A-Za-z\\s-]*$",
                message = "Last name must start with uppercase"
        )
        @JsonProperty("last_name")
        String lastName,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "^(19[0-9]{2}|200[0-5])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
                message = "Date of birth must be between 1900 and 2005 "
        )
        @JsonProperty("dob")
        String birthDate,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "^(male|female)$", flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "The flag must be like these: " +
                        "\"male\" and \"female\" " +
                        "in any case e.g. \"male\", \"Male\", \"FEMALE\", etc."
        )
        @JsonProperty("gender")
        String gender,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "[A-Z]{2}\\d{7}",
                message = "'passport number' must have 2 uppercase letters followed by 7 digits"
        )
        @JsonProperty("passport_number")
        String passportNumber,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = com.example.bank.validator.valid_classes.Pattern.DATE_PATTERN,
                message = "Regex for 'issueDate': yyyy-mm-dd"
        )
        @JsonProperty("issue_date")
        String issueDate,

        @NotNullEmptyBlankString

        @Pattern(
                regexp = com.example.bank.validator.valid_classes.Pattern.DATE_PATTERN,
                message = "Regex for 'expiryDate': yyyy-mm-dd"
        )

        @JsonProperty("expiry_date")
        String expiryDate,

        @NotNullEmptyBlankString
        @Length(
                min = 3,
                max = 6,
                message = "'authority' length must be in range[3; 6]"
        )
        @JsonProperty("authority")
        String authority
) {

        public static PassportRequest getFromResponse(final PassportResponse passportResponse) {
                return new PassportRequest(
                        passportResponse.firstName(),
                        passportResponse.lastName(),
                        passportResponse.birthDate(),
                        passportResponse.gender(),
                        passportResponse.passportNumber(),
                        passportResponse.issueDate(),
                        passportResponse.expiryDate(),
                        passportResponse.authority());
        }

        public static PassportRequest getFromModel(final PassportModel passportModel) {
                return new PassportRequest(
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

        @Override
        public String toString() {
                return "PassportRequest{" +
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