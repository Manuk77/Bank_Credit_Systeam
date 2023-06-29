package com.example.bank.customer.creating_requests.requests;

import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.response.CreditResponse;
import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.example.bank.validator.annotation.ValidCreditDates;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

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
        @JsonProperty("is_accepted")
        String isAccepted,
        @NotNullEmptyBlankString
        @JsonProperty("is_risk_accepted")
        String isRiskAccepted,

        @NotNullEmptyBlankString
        @Pattern(regexp = "^\\d+(\\.\\d+)?%?$")
        @JsonProperty("percent")
        String percent
) {

    public static CreditRequest getFromResponse(final CreditResponse creditResponse) {
        return new CreditRequest(
                creditResponse.bankName(),
                creditResponse.loanAmount(),
                creditResponse.creditType(),
                creditResponse.paymentPerMonth(),
                creditResponse.startCreditDate(),
                creditResponse.endCreditDate(),
                creditResponse.creditState(),
                creditResponse.isAccepted(),
                creditResponse.isRiskAccepted(),
                creditResponse.percent());
    }

    public static CreditRequest getFromModel(final CreditModel creditModel) {
        return new CreditRequest(
                creditModel.getBankName().toString(),
                creditModel.getLoanAmount(),
                creditModel.getCreditType().toString(),
                creditModel.getPaymentPerMonth(),
                creditModel.getStartCreditDate().toString(),
                creditModel.getEndCreditDate().toString(),
                creditModel.getCreditState().toString(),
                creditModel.getAccepted().toString(),
                creditModel.getRiskAccepted().toString(),
                creditModel.getPercent().toString()

        );
    }

    public static List<CreditResponse> getListFromRequest(final List<CreditRequest> creditRequests) {
        return creditRequests.stream()
                .map(creditRequest -> new CreditResponse(
                        creditRequest.bankName(),
                        creditRequest.loanAmount(),
                        creditRequest.creditType(),
                        creditRequest.paymentPerMonth(),
                        creditRequest.startCreditDate(),
                        creditRequest.endCreditDate(),
                        creditRequest.creditState(),
                        creditRequest.isAccepted(),
                        creditRequest.isRiskAccepted(),
                        creditRequest.percent()
                )).toList();
    }


}