package com.example.bank.customer.creating_requests.requests;

import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.example.bank.validator.annotation.ValidCreditDates;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

@ValidCreditDates(message = "Invalid start/end credit date for CreditUpdateRequest: ")

public record CreditRequest(
        @NotNullEmptyBlankString
        @Pattern(regexp = "^[A-Za-z\\s]+$")
        @JsonProperty("bank_name")
        String bankName,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^([֏$€₽])?\\d+(\\.\\d+)?$")
        @JsonProperty("loan_amount")
        String loanAmount,

        @NotNullEmptyBlankString
        @JsonProperty("credit_type")
        String creditType,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^([֏$€₽])?\\d+(\\.\\d+)?$")
        @JsonProperty("payment_per_month")
        String paymentPerMonth,

        @NotNullEmptyBlankString
        @JsonProperty("start_date")
        String startCreditDate,

        @NotNullEmptyBlankString
        @JsonProperty("end_date")
        String endCreditDate,

        @NotNullEmptyBlankString
        @JsonProperty("credit_state")
        String creditState,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^\\d+(\\.\\d+)?%?$")
        @JsonProperty("percent")
        String percent
) {
}