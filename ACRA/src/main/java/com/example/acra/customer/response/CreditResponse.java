package com.example.acra.customer.response;

import com.example.acra.customer.dto.CreditModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName(value = "credits")
public record CreditResponse(
        @JsonProperty("bank_name")
        String bankName,
        @JsonProperty("loan_amount")
        String loanAmount,
        @JsonProperty("credit_type")
        String creditType,
        @JsonProperty("payment_per_month")
        String paymentPerMonth,
        @JsonProperty("start_date")
        String startCreditDate,
        @JsonProperty("end_date")
        String endCreditDate,
        @JsonProperty("is_accepted")
        String isAccepted,
        @JsonProperty("credit_state")
        String creditState,
        @JsonProperty("is_risk_accepted")
        String isRiskAccepted,
        @JsonProperty("percent")
        String percent
) {
    public static CreditResponse getFromModel(CreditModel creditModel) {
        return new CreditResponse(
                creditModel.getBankName().toString(),
                creditModel.getLoanAmount(),
                creditModel.getCreditType().toString(),
                creditModel.getPaymentPerMonth(),
                creditModel.getStartCreditDate().toString(),
                creditModel.getEndCreditDate().toString(),
                creditModel.getAccepted().toString(),
                creditModel.getCreditState().toString(),
                creditModel.getRiskAccepted().toString(),
                creditModel.getPercent().toString()
        );
    }

    public static List<CreditResponse> getListFromModel(List<CreditModel> creditModels) {
        return creditModels.stream()
                .map(creditModel -> new CreditResponse(
                        creditModel.getBankName().toString(),
                        creditModel.getLoanAmount(),
                        creditModel.getCreditType().toString(),
                        creditModel.getPaymentPerMonth(),
                        creditModel.getStartCreditDate().toString(),
                        creditModel.getEndCreditDate().toString(),
                        creditModel.getAccepted().toString(),
                        creditModel.getCreditState().toString(),
                        creditModel.getRiskAccepted().toString(),
                        creditModel.getPercent().toString()))
                .toList();
    }
}
