package com.example.acra.customer.entity;

import com.example.acra.customer.dto.WorkingPlaceModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "working_place")
public class WorkingPlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "salary", nullable = false, length = 12)
    private String salary;

    @OneToMany(mappedBy = "workingPlace")
    private List<CustomerEntity> customerEntities;

    public WorkingPlaceEntity() {
    }

    public WorkingPlaceEntity(final String name, final String salary,
                              final List<CustomerEntity> customerEntities) {
        this.name = name;
        this.salary = salary;
        this.customerEntities = customerEntities;
    }

    public WorkingPlaceEntity(final WorkingPlaceModel workingPlaceModel) {
        this.salary = workingPlaceModel.getSalary();
        this.name = workingPlaceModel.getName();
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

    public List<CustomerEntity> getCustomerEntities() {
        return customerEntities;
    }

    public void setCustomerEntities(List<CustomerEntity> customerEntities) {
        this.customerEntities = customerEntities;
    }

}
