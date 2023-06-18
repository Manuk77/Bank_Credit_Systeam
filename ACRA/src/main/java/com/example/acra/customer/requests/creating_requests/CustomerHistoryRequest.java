package com.example.acra.customer.requests.creating_requests;


import com.example.acra.annotation.NotNullEmptyBlankString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;



import java.util.List;

public record CustomerHistoryRequest(

        @NotNullEmptyBlankString
        @Pattern(regexp = "^([֏$€₽])?\\d+(\\.\\d+)?$")
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

        

        @JsonProperty("credits")
        List<CreditRequest> creditRequest
) {

}
