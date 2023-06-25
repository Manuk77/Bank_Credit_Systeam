package com.example.bank.customer.entity;


import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerHistoryModel;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_history")
public class CustomerHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;
    @Column(name = "salary", nullable = false, length = 10)
    private String salary;
    @Column(name = "has_active_credit", nullable = false)
    private Boolean hasActiveCredit;

    // credit score is a score which helps to count the risks of giving credit to the customer
    // it's also noun as FICO score it's around [300 - 850] how high is the score that match lower is risk
    @Column(name = "credit_score", nullable = false)
    private short creditScore;
    @OneToOne(mappedBy = "customerHistory")
    private CustomerEntity customerEntity;
    @OneToMany(mappedBy = "customerHistoryEntity")
    private List<CreditEntity> credits;

    public CustomerHistoryEntity() {
    }

    public CustomerHistoryEntity(final String salary, final Boolean hasActiveCredit,
                                 final short creditScore, final List<CreditEntity> credits) {

        this.salary = salary;
        this.hasActiveCredit = hasActiveCredit;
        this.creditScore = creditScore;
        this.credits = credits;

    }

    public CustomerHistoryEntity(final CustomerHistoryModel customerHistoryModel) {

        this.creditScore = customerHistoryModel.getCreditScore();
        this.salary = customerHistoryModel.getSalary();
        this.hasActiveCredit = customerHistoryModel.getHasActiveCredit();
        this.credits = castToCreditModel(customerHistoryModel.getCreditModels());
    }


    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Boolean getHasActiveCredit() {
        return hasActiveCredit;
    }

    public void setHasActiveCredit(Boolean hasActiveCredit) {
        this.hasActiveCredit = hasActiveCredit;
    }

    public short getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(short creditScore) {
        this.creditScore = creditScore;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public List<CreditEntity> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditEntity> credits) {
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    private List<CreditEntity> castToCreditModel(final List<CreditModel> creditModels) {
        List<CreditEntity> creditEntities = new ArrayList<>();
        creditModels.forEach(creditModel -> creditEntities.add(new CreditEntity(creditModel)));
        return  creditEntities;
    }


}
