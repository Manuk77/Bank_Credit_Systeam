package com.example.acra.customer.dto;

import com.example.acra.customer.bank.Banks;
import com.example.acra.customer.bank.CreditType;
import com.example.acra.customer.entity.CreditEntity;
import com.example.acra.customer.requests.creating_requests.CreditRequest;
import com.example.acra.customer.response.CreditResponse;

import java.sql.Date;

public class CreditModel {
    private Banks bankName;
    private String loanAmount;
    private CreditType creditType;
    private String paymentPerMonth;
    private Date startCreditDate;
    private Date endCreditDate;
    private Boolean creditState;
    private Boolean isAccepted;
    private Boolean isRiskAccepted;
    private Byte percent;


    public CreditModel() {
    }

    public CreditModel(final CreditEntity creditEntity) {
        this.creditType = CreditType.valueOf(creditEntity.getCreditType());
        this.bankName = Banks.valueOf(creditEntity.getBankName());
        this.endCreditDate = creditEntity.getEndCreditDate();
        this.percent = creditEntity.getPercent();
        this.loanAmount = creditEntity.getLoanAmount();
        this.startCreditDate = creditEntity.getStartCreditDate();
        this.paymentPerMonth = creditEntity.getPaymentPerMonth();
        this.creditState = creditEntity.getCreditState();
        this.isAccepted = creditEntity.getAccepted();
        this.isRiskAccepted = creditEntity.getRiskAccepted();

    }

    public CreditModel(final CreditRequest creditRequest) {
        this.paymentPerMonth = creditRequest.paymentPerMonth();
        this.startCreditDate = Date.valueOf(creditRequest.startCreditDate());
        this.creditType = CreditType.valueOf(creditRequest.creditType());
        this.percent = Byte.valueOf(creditRequest.percent());
        this.loanAmount = creditRequest.loanAmount();
        this.endCreditDate = Date.valueOf(creditRequest.endCreditDate());
        this.bankName = Banks.valueOf(creditRequest.bankName());
        this.creditState = Boolean.valueOf(creditRequest.creditState());
        this.isAccepted = Boolean.valueOf(creditRequest.isAccepted());
        this.isRiskAccepted = Boolean.valueOf(creditRequest.isRiskAccepted());
    }
    public CreditModel(final CreditResponse creditRequest) {
        this.paymentPerMonth = creditRequest.paymentPerMonth();
        this.startCreditDate = Date.valueOf(creditRequest.startCreditDate());
        this.creditType = CreditType.valueOf(creditRequest.creditType());
        this.percent = Byte.valueOf(creditRequest.percent());
        this.loanAmount = creditRequest.loanAmount();
        this.endCreditDate = Date.valueOf(creditRequest.endCreditDate());
        this.bankName = Banks.valueOf(creditRequest.bankName());
        this.creditState = Boolean.valueOf(creditRequest.creditState());
        this.isAccepted = Boolean.valueOf(creditRequest.isAccepted());
        this.isRiskAccepted = Boolean.valueOf(creditRequest.isRiskAccepted());

    }




    public Banks getBankName() {
        return bankName;
    }

    public void setBankName(Banks bankName) {
        this.bankName = bankName;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public String getPaymentPerMonth() {
        return paymentPerMonth;
    }

    public void setPaymentPerMonth(String paymentPerMonth) {
        this.paymentPerMonth = paymentPerMonth;
    }

    public Date getStartCreditDate() {
        return startCreditDate;
    }

    public void setStartCreditDate(Date startCreditDate) {
        this.startCreditDate = startCreditDate;
    }

    public Date getEndCreditDate() {
        return endCreditDate;
    }

    public void setEndCreditDate(Date endCreditDate) {
        this.endCreditDate = endCreditDate;
    }

    public Byte getPercent() {
        return percent;
    }

    public void setPercent(byte percent) {
        this.percent = percent;
    }

    public Boolean getCreditState() {
        return creditState;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public void setCreditState(Boolean creditState) {
        this.creditState = creditState;
    }

    public void setPercent(Byte percent) {
        this.percent = percent;
    }

    public Boolean getRiskAccepted() {
        return isRiskAccepted;
    }

    public void setRiskAccepted(Boolean riskAccepted) {
        isRiskAccepted = riskAccepted;
    }

    @Override
    public String toString() {
        return "CreditModel{" +
                "bankName=" + bankName +
                ", loanAmount='" + loanAmount + '\'' +
                ", creditType=" + creditType +
                ", paymentPerMonth='" + paymentPerMonth + '\'' +
                ", startCreditDate=" + startCreditDate +
                ", endCreditDate=" + endCreditDate +
                ", creditState=" + creditState +
                ", isAccepted=" + isAccepted +
                ", percent=" + percent +
                '}';
    }
}
