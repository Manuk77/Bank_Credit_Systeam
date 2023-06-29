package com.example.bank.customer.dto;

import com.example.bank.bank_model.default_calculating.CreditHistoryType;
import com.example.bank.customer.bank.CreditType;

public class ResultCustomerInfoModel {
    private final String fName;
    private final String lName;
    private final  Integer customerAge;
    private final Integer customerIncome;
    private final CreditType creditType;
    private final CreditHistoryType creditHistoryType;
    private final Integer loanAmount;
    private final Integer creditTime;
    private Integer y;
    private Double L;
    private Double R;


    public ResultCustomerInfoModel(final CustomerModelFiltered customerModelFiltered){
        this.fName = customerModelFiltered.getfName();
        this.lName = customerModelFiltered.getlName();
        this.customerAge = customerModelFiltered.getCustomerAge();
        this.customerIncome = customerModelFiltered.getCustomerIncome();
        this.creditType = customerModelFiltered.getCreditType();
        this.creditHistoryType = customerModelFiltered.getCreditHistoryType();
        this.loanAmount = customerModelFiltered.getLoanAmount();
        this.creditTime = customerModelFiltered.getCreditTime();
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

    public void setL(final Double l) {
        L = l;
    }

    public void setR(final Double r) {
        R = r;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public void setY(Integer y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "ResultCustomerInfoModel{" +
                "customerAge=" + customerAge +
                ", customerIncome=" + customerIncome +
                ", creditType=" + creditType +
                ", creditHistoryType=" + creditHistoryType +
                ", loanAmount=" + loanAmount +
                ", creditTime=" + creditTime +
                ", y=" + y +
                ", L=" + L +
                ", R=" + R +
                '}';
    }
}
