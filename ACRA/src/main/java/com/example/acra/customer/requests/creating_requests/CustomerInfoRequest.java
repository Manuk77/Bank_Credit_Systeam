package com.example.acra.customer.requests.creating_requests;

import com.example.acra.validation.annotations.NotNullEmptyBlankString;
import com.example.acra.validation.annotations.ValidAge;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
@ValidAge
public record CustomerInfoRequest(


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
                regexp = "^(1[89]|[2-9][0-9])$",
                message = "Customer must be older than 18"
        )
        @JsonProperty("age")
        String age,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "^\\+374\\s\\d{2}-\\d{2}-\\d{2}-\\d{2}$",
                message = """
                        The phone number must be like these:\s
                       +374 11-11-11-11"""
        )
        @JsonProperty("phone")
        String phone,


        @NotNullEmptyBlankString
        @Email
        @JsonProperty("email")
        String email

) {

}
