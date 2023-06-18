package com.example.bank.bank_model;

import com.example.bank.bank_entity.Employee;
import com.example.bank.customer.entity.CustomerEntity;

import java.sql.Date;
import java.util.List;


public class BankModel {

    private String name;

    private Date foundDate;

    private String capital;

    private List<Employee> employees;

    private List<CustomerEntity> customerEntities;

    public BankModel() {
    }

    public BankModel(final String name, final Date foundDate,
                     final String capital, final List<Employee> employees,
                     final List<CustomerEntity> customerEntities) {
        this.name = name;
        this.foundDate = foundDate;
        this.capital = capital;
        this.employees = employees;
        this.customerEntities = customerEntities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<CustomerEntity> getCustomerEntities() {
        return customerEntities;
    }

    public void setCustomerEntities(List<CustomerEntity> customerEntities) {
        this.customerEntities = customerEntities;
    }



}
