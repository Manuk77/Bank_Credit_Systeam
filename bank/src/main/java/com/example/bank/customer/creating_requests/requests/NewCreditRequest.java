package com.example.bank.customer.creating_requests.requests;

import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import org.springframework.lang.NonNull;

public record NewCreditRequest(

        @NotNullEmptyBlankString
        @Pattern(regexp = "^(֏)?\\d+(\\.\\d+)?$")
        @JsonProperty("loan_amount")
        String loanAmount,

        @NotNullEmptyBlankString
        @JsonProperty("credit_type")
        String creditType,

        @NotNullEmptyBlankString
        @JsonProperty("credit_time")
        String creditTime,

        @NotNullEmptyBlankString
        @JsonProperty("bank_name")
        String bankName

) {
}
