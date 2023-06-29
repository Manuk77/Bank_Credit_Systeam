package com.example.bank.customer.response;

import com.example.bank.customer.dto.CreditModel;
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
        @JsonProperty("credit_state")
        String creditState,
        @JsonProperty("is_accepted")
        String isAccepted,
        @JsonProperty("is_risk_accepted")
        String isRiskAccepted,

        @JsonProperty("percent")
        String percent
) {
    public static CreditResponse getFromModel(CreditModel creditModel){
        return  new
                CreditResponse(
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
    public static List<CreditResponse> getListFromModel(List<CreditModel> creditModels){
        return creditModels.stream()
                .map(creditModel -> new CreditResponse(
                        creditModel.getBankName().toString(),
                        creditModel.getLoanAmount(),
                        creditModel.getCreditType().toString(),
                        creditModel.getPaymentPerMonth(),
                        creditModel.getStartCreditDate().toString(),
                        creditModel.getEndCreditDate().toString(),
                        creditModel.getCreditState().toString(),
                        creditModel.getAccepted().toString(),
                        creditModel.getRiskAccepted().toString(),
                        creditModel.getPercent().toString()))
                .toList();
    }

    @Override
    public String toString() {
        return "CreditResponse{" +
                "bankName='" + bankName + '\'' +
                ", loanAmount='" + loanAmount + '\'' +
                ", creditType='" + creditType + '\'' +
                ", paymentPerMonth='" + paymentPerMonth + '\'' +
                ", startCreditDate='" + startCreditDate + '\'' +
                ", endCreditDate='" + endCreditDate + '\'' +
                ", percent='" + percent + '\'' +
                '}';
    }
}
