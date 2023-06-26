package com.example.bank.customer.creating_requests.requests;

import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerHistoryModel;
import com.example.bank.customer.response.CreditResponse;
import com.example.bank.customer.response.CustomerHistoryResponse;
import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;


import java.util.List;

public record CustomerHistoryRequest(

        @NotNullEmptyBlankString
        @Pattern(regexp = "^([6-9]\\d{4}|[1-9]\\d{5,})$") //"^([֏$€₽])?\\d+(\\.\\d+)?$"
        @JsonProperty("salary")
        String salary,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "^(true|false)$", flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "The flag must be like these: " +
                        "\"true\" and \"false\" " +
                        "in any case e.g., \"true\", \"True\", \"FALSE\", etc."
        )
        @JsonProperty("has_active_credit")
        String hasActiveCredit,

        @NotNullEmptyBlankString
        @Pattern(
                regexp = "^(?:[3-7][0-9]{2}|8[0-4][0-9]|850)$",
                message = "Credit score must be in range 300-850"
        )
        @JsonProperty("credit_score")
        String creditScore,

        @Valid
        @JsonProperty("credits")
        List<CreditRequest> creditRequest
) {

    public static CustomerHistoryRequest getFromResponse(final CustomerHistoryResponse customerHistoryResponse) {
        return new CustomerHistoryRequest(
                customerHistoryResponse.salary(),
                customerHistoryResponse.hasActiveCredit(),
                customerHistoryResponse.creditScore(),
                convFromResponse(customerHistoryResponse.creditResponse()));
    }

    public static CustomerHistoryRequest getFromModel(final CustomerHistoryModel customerHistoryModel) {
        return new CustomerHistoryRequest(
                customerHistoryModel.getSalary(),
                customerHistoryModel.getHasActiveCredit().toString(),
                customerHistoryModel.getCreditScore().toString(),
                convFromModel(customerHistoryModel.getCreditModels())

        );
    }


    private static List<CreditRequest> convFromResponse(final List<CreditResponse> creditResponses) {
        return creditResponses.stream().map(CreditRequest::getFromResponse).toList();
    }

    private static List<CreditRequest> convFromModel(final List<CreditModel> creditModels) {
        return creditModels.stream().map(CreditRequest::getFromModel).toList();
    }

    @Override
    public String toString() {
        return "CustomerHistoryRequest{" +
                "salary='" + salary + '\'' +
                ", hasActiveCredit='" + hasActiveCredit + '\'' +
                ", creditScore='" + creditScore + '\'' +
                ", creditRequest=" + creditRequest +
                '}';
    }
}