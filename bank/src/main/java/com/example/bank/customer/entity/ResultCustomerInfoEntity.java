package com.example.bank.customer.entity;

import com.example.bank.bank_model.default_calculating.CreditHistoryType;
import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.dto.ResultCustomerInfoModel;
import jakarta.persistence.*;

@Entity
@Table(name = "result_table")
public class ResultCustomerInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name", nullable = false, length = 15)
    private String fName;
    @Column(name = "last_name", nullable = false, length = 15)
    private String lName;
    @Column(name = "customer_age", nullable = false, length = 15)
    private Integer customerAge;
    @Column(name = "customer_income", nullable = false, length = 15)
    private Integer customerIncome;
    @Column(name = "credit_type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private CreditType creditType;
    @Column(name = "credit_history_type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private CreditHistoryType creditHistoryType;
    @Column(name = "loan_amount", nullable = false, length = 15)
    private Integer loanAmount;
    @Column(name = "credit_time", nullable = false, length = 15)
    private Integer creditTime;
    @Column(name = "y", nullable = false, length = 15)
    private Integer y;
    @Column(name = "risk_L", length = 15)
    private Double L;
    @Column(name = "R", length = 15)
    private  Double R;

    public ResultCustomerInfoEntity() {
    }

    public ResultCustomerInfoEntity(final Integer customerAge, final Integer customerIncome,
                                    final CreditType creditType, final CreditHistoryType creditHistoryType,
                                    final Integer loanAmount, final Integer creditTime, final Integer y, final Double l, Double r) {
        this.customerAge = customerAge;
        this.customerIncome = customerIncome;
        this.creditType = creditType;
        this.creditHistoryType = creditHistoryType;
        this.loanAmount = loanAmount;
        this.creditTime = creditTime;
        this.y = y;
        L = l;
        R = r;
    }

    public ResultCustomerInfoEntity(final ResultCustomerInfoModel resultCustomerInfoModel) {
        this.fName = resultCustomerInfoModel.getfName();
        this.lName = resultCustomerInfoModel.getlName();
        this.customerAge = resultCustomerInfoModel.getCustomerAge();
        this.customerIncome = resultCustomerInfoModel.getCustomerIncome();
        this.creditType = resultCustomerInfoModel.getCreditType();
        this.creditTime = resultCustomerInfoModel.getCreditTime();
        this.creditHistoryType = resultCustomerInfoModel.getCreditHistoryType();
        this.loanAmount = resultCustomerInfoModel.getLoanAmount();
        this.y = resultCustomerInfoModel.getY();
        this.R = resultCustomerInfoModel.getR();
        this.L = resultCustomerInfoModel.getL();
    }


    public Integer getCustomerAge() {
        return customerAge;
    }

    public Integer getCustomerIncome() {
        return customerIncome;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public CreditHistoryType getCreditHistoryType() {
        return creditHistoryType;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public Integer getCreditTime() {
        return creditTime;
    }

    public Integer getY() {
        return y;
    }

    public Double getL() {
        return L;
    }

    public Double getR() {
        return R;
    }
}
