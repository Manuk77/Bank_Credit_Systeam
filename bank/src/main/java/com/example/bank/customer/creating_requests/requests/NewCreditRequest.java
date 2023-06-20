package com.example.bank.customer.creating_requests.requests;

import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record NewCreditRequest(

        @NotNullEmptyBlankString
        @Pattern(regexp = "^(֏)?\\d+(\\.\\d+)?$")
        @JsonProperty("loanAmount")
        String loanAmount,

        @NotNullEmptyBlankString
        @JsonProperty("creditType")
        String creditType,

        @NotNullEmptyBlankString
        @JsonProperty("creditTime")
        String creditTime,

        @NotNullEmptyBlankString
        @JsonProperty("bank_name")
        String bankName
) {
}
