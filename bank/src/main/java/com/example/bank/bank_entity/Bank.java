package com.example.bank.bank_entity;

import com.example.bank.customer.entity.CustomerEntity;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "foundDate", nullable = false)
    private Date foundDate;
    @Column(name = "capital", length = 255, nullable = false)
    private String capital;
    @OneToMany(mappedBy = "bank")
    private List<Employee> employees;
    @OneToMany(mappedBy = "bank")
    private List<CustomerEntity> customerEntities;

    public Bank() {
    }

    public Bank(final String name, final Date foundDate, final String capital,
                final List<Employee> employees, final List<CustomerEntity> customerEntities) {
        this.name = name;
        this.foundDate = foundDate;
        this.capital = capital;
        this.employees = employees;
        this.customerEntities = customerEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(final Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(final String capital) {
        this.capital = capital;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(final List<Employee> employees) {
        this.employees = employees;
    }

    public List<CustomerEntity> getCustomerEntities() {
        return customerEntities;
    }

    public void setCustomerEntities(final List<CustomerEntity> customerEntities) {
        this.customerEntities = customerEntities;
    }
}
