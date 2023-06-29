package com.example.bank.bank_entity;

import com.example.bank.bank_model.BankModel;
import com.example.bank.customer.entity.CustomerEntity;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bank")
public class BankEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 15)
    private String name;
    @Column(name = "foundDate", nullable = false, length = 15)
    private Date foundDate;
    @Column(name = "capital",  nullable = false, length = 50)
    private String capital;
    @OneToMany(mappedBy = "bank")
    private List<CustomerEntity> customerEntities;

    public BankEntity() {
    }

    public BankEntity(final BankModel bankModel) {
        this.name = bankModel.getName();
        this.capital = bankModel.getCapital();
        this.foundDate = Date.valueOf(LocalDate.now());
    }

    public BankEntity(final String name, final String capital) {
        this.name = name;
        this.foundDate = Date.valueOf(LocalDate.now());
        this.capital = capital;
        this.customerEntities = null;
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

    public List<CustomerEntity> getCustomerEntities() {
        return customerEntities;
    }

    public void setCustomerEntities(final List<CustomerEntity> customerEntities) {
        this.customerEntities = customerEntities;
    }
}
