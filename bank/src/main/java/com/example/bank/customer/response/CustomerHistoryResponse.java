package com.example.bank.customer.response;

import com.example.bank.customer.dto.CustomerHistoryModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;
@JsonRootName(value = "credit_history")
public record CustomerHistoryResponse(
        @JsonProperty("salary")
        String salary,
        @JsonProperty("has_active_credits")
        String hasActiveCredit,
        @JsonProperty("credit_score")
        String creditScore,
        @JsonProperty("credits")
        List<CreditResponse> creditResponse
) {
    public static CustomerHistoryResponse getFromModel(CustomerHistoryModel customerHistoryModel){
        return  new
                CustomerHistoryResponse(
                customerHistoryModel.getSalary(),
                customerHistoryModel.getHasActiveCredit().toString(),
                customerHistoryModel.getCreditScore().toString(),
                CreditResponse.getListFromModel(customerHistoryModel.getCreditModels())
        );
    }

    @Override
    public String toString() {
        return "CustomerHistoryResponse{" +
                "salary='" + salary + '\'' +
                ", hasActiveCredit='" + hasActiveCredit + '\'' +
                ", creditScore='" + creditScore + '\'' +
                ", creditResponse=" + creditResponse +
                '}';
    }
}
