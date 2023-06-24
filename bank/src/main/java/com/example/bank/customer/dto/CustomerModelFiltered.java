package com.example.bank.customer.dto;

import com.example.bank.bank_model.risk_calculating.CreditHistoryType;
import com.example.bank.customer.bank.CreditType;

public class CustomerModelFiltered {
    private final  Integer customerAge;
    private Integer customerIncome;
    private CreditType creditType;
    private final CreditHistoryType creditHistoryType;
    private Integer loanAmount;
    private Integer creditTime;

    public CustomerModelFiltered(final Integer customerAge, final Integer customerIncome, final CreditType creditType,
                                 final CreditHistoryType creditHistoryType, final Integer loanAmount, final Integer creditTime) {
        this.customerAge = customerAge;
        this.customerIncome = customerIncome;
        this.creditType = creditType;
        this.creditHistoryType = creditHistoryType;
        this.loanAmount = loanAmount;
        this.creditTime = creditTime;
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

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public CreditHistoryType getCreditHistoryType() {
        return creditHistoryType;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getCreditTime() {
        return creditTime;
    }

    public void setCustomerIncome(Integer customerIncome) {
        this.customerIncome = customerIncome;
    }

    public void setCreditTime(Integer creditTime) {
        this.creditTime = creditTime;
    }
}
