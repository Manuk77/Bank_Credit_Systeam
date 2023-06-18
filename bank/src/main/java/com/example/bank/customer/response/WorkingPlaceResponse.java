package com.example.bank.customer.response;

import com.example.bank.customer.dto.WorkingPlaceModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "working_place")
public record WorkingPlaceResponse(
        @JsonProperty("name")
        String name,
        @JsonProperty("salary")
        String salary
) {
    public static WorkingPlaceResponse getFromModel(WorkingPlaceModel workingPlaceModel){
        return new
                WorkingPlaceResponse(
                workingPlaceModel.getName(),
                workingPlaceModel.getSalary()
        );
    }

    @Override
    public String toString() {
        return "WorkingPlaceResponse{" +
                "name='" + name + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}