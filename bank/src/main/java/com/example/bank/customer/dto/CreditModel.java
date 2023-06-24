package com.example.bank.customer.dto;



import com.example.bank.customer.bank.Banks;
import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.creating_requests.requests.CreditRequest;
import com.example.bank.customer.entity.CreditEntity;
import com.example.bank.customer.response.CreditResponse;

import java.sql.Date;

public class CreditModel {
    private Banks bankName;
    private String loanAmount;
    private CreditType creditType;
    private String paymentPerMonth;
    private Date startCreditDate;
    private Date endCreditDate;
    private Byte percent;
    private Boolean creditState;

    private Boolean isAccepted;


    public CreditModel(final CreditType creditType, final Banks bankName, final String loanAmount,
                       final Date startCreditDate, final Date endCreditDate, final String paymentPerMonth,
                       final Byte percent, final Boolean creditState, final Boolean isAccepted) {

        this.bankName = bankName;
        this.creditType = creditType;
        this.startCreditDate = startCreditDate;
        this.endCreditDate = endCreditDate;
        this.percent = percent;
        this.paymentPerMonth = paymentPerMonth;
        this.loanAmount = loanAmount;
        this.creditState = creditState;
        this.isAccepted = isAccepted;
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
    }

    public CreditModel(final CreditResponse creditResponse) {
        this.paymentPerMonth = creditResponse.paymentPerMonth();
        this.startCreditDate = Date.valueOf(creditResponse.startCreditDate());
        this.creditType = CreditType.valueOf(creditResponse.creditType());
        this.percent = Byte.valueOf(creditResponse.percent());
        this.loanAmount = creditResponse.loanAmount();
        this.endCreditDate = Date.valueOf(creditResponse.endCreditDate());
        this.bankName = Banks.valueOf(creditResponse.bankName());
        this.creditState = Boolean.valueOf(creditResponse.creditState());
        this.isAccepted = Boolean.valueOf(creditResponse.isAccepted());
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

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public void setPercent(byte percent) {
        this.percent = percent;
    }

    public void setPercent(Byte percent) {
        this.percent = percent;
    }

    public Boolean getCreditState() {
        return creditState;
    }

    public void setCreditState(Boolean creditState) {
        this.creditState = creditState;
    }
}
