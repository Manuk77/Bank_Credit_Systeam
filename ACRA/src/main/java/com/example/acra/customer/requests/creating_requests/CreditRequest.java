package com.example.acra.customer.requests.creating_requests;


import com.example.acra.validation.annotations.ValidCreditDates;
import com.example.acra.validation.annotations.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

@ValidCreditDates(message = "Invalid start/end credit date for CreditUpdateRequest: ")

public record CreditRequest(
        @NotNullEmptyBlankString
        @Pattern(regexp = "^[A-Za-z\\s]+$")
        @JsonProperty("bank_name")
        String bankName,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^(֏|\\$|€|₽)?\\d+(\\.\\d+)?$")
        @JsonProperty("loan_amount")
        String loanAmount,

        @NotNullEmptyBlankString
        @JsonProperty("credit_type")
        String creditType,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^(֏|\\$|€|₽)?\\d+(\\.\\d+)?$")
        @JsonProperty("payment_per_month")
        String paymentPerMonth,

        @NotNullEmptyBlankString
        @JsonProperty("start_date")
        String startCreditDate,

        @NotNullEmptyBlankString
        @JsonProperty("end_date")
        String endCreditDate,

        @NotNullEmptyBlankString
        @JsonProperty("is_accepted")
        String isAccepted,

        @NotNullEmptyBlankString
        @JsonProperty("credit_state")
        String creditState,
        @NotNullEmptyBlankString
        @JsonProperty("is_risk_accepted")
        String isRiskAccepted,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^\\d+(\\.\\d+)?%?$")
        @JsonProperty("percent")
        String percent
) {
}
