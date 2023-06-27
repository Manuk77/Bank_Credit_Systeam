package com.example.bank.customer.dto;


import com.example.bank.customer.creating_requests.requests.WorkingPlaceRequest;
import com.example.bank.customer.entity.WorkingPlaceEntity;
import com.example.bank.customer.response.WorkingPlaceResponse;

import java.util.Objects;


public class WorkingPlaceModel {
    private String name;
    private String salary;

    public WorkingPlaceModel(final WorkingPlaceRequest workingPlaceRequest) {
        this.salary = workingPlaceRequest.salary();
        this.name = workingPlaceRequest.name();
    }
    public WorkingPlaceModel(final WorkingPlaceResponse workingPlaceResponse) {
        this.salary = workingPlaceResponse.salary();
        this.name = workingPlaceResponse.name();
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

    @Override
    public String toString() {
        return "WorkingPlaceModel{" +
                "name='" + name + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingPlaceModel that = (WorkingPlaceModel) o;
        return Objects.equals(name, that.name) && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary);
    }
}
