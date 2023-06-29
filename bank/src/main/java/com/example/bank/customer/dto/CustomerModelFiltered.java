package com.example.bank.customer.dto;

import com.example.bank.bank_model.default_calculating.CreditHistoryType;
import com.example.bank.customer.bank.CreditType;

public class CustomerModelFiltered {
    private final String fName;
    private final String lName;
    private final  Integer customerAge;
    private Integer customerIncome;
    private CreditType creditType;
    private final CreditHistoryType creditHistoryType;
    private Integer loanAmount;
    private Integer creditTime;

    public CustomerModelFiltered(final String fName, final String lName,
                                 final Integer customerAge, final Integer customerIncome,
                                 final CreditType creditType, final CreditHistoryType creditHistoryType,
                                 final Integer loanAmount, final Integer creditTime) {
        this.customerAge = customerAge;
        this.customerIncome = customerIncome;
        this.creditType = creditType;
        this.creditHistoryType = creditHistoryType;
        this.loanAmount = loanAmount;
        this.creditTime = creditTime;
        this.fName = fName;
        this.lName = lName;
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

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    @Override
    public String toString() {
        return "CustomerModelFiltered{" +
                "customerAge=" + customerAge +
                ", customerIncome=" + customerIncome +
                ", creditType=" + creditType +
                ", creditHistoryType=" + creditHistoryType +
                ", loanAmount=" + loanAmount +
                ", creditTime=" + creditTime +
                '}';
    }
}
