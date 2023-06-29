package com.example.acra.customer.dto;

import com.example.acra.customer.entity.WorkingPlaceEntity;
import com.example.acra.customer.requests.creating_requests.WorkingPlaceRequest;
import com.example.acra.customer.response.WorkingPlaceResponse;

public class WorkingPlaceModel {
    private String name;
    private String salary;

    public WorkingPlaceModel(final WorkingPlaceRequest workingPlaceRequest) {
        this.salary = workingPlaceRequest.salary();
        this.name = workingPlaceRequest.name();
    }
    public WorkingPlaceModel(final WorkingPlaceResponse workingPlaceRequest) {
        this.salary = workingPlaceRequest.salary();
        this.name = workingPlaceRequest.name();
    }

    public WorkingPlaceModel(final WorkingPlaceEntity workingPlaceEntity) {
        this.name = workingPlaceEntity.getName();
        this.salary = workingPlaceEntity.getSalary();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
