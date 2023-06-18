package com.example.acra.customer.response;

import com.example.acra.customer.dto.CustomerInfoModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "personal_info")
public record CustomerInfoResponse(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("dob")
        String birthDate,
        @JsonProperty("age")
        String age,
        @JsonProperty("phone")
        String phone,
        @JsonProperty("email")
        String email

) {
    public static CustomerInfoResponse getFromModel(CustomerInfoModel customerInfoModel){
        return new CustomerInfoResponse(
                customerInfoModel.getFirstName(),
                customerInfoModel.getLastName(),
                customerInfoModel.getBirthDate(),
                customerInfoModel.getAge().toString(),
                customerInfoModel.getPhone(),
                customerInfoModel.getEmail()
        );

    }
}
