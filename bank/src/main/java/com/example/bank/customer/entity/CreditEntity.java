package com.example.bank.customer.entity;




import com.example.bank.customer.bank.Banks;
import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.dto.CreditModel;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "credits")
public class CreditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long id;
    @Column(name = "bank_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Banks bankName;
    @Column(name = "loan_amount", nullable = false, length = 15)
    private String loanAmount;
    @Column(name = "credit_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreditType creditType;
    @Column(name = "payment_per_month", nullable = false, length = 30)
    private String paymentPerMonth;
    @Column(name = "start_credit_date", nullable = false)
    private Date startCreditDate;
    @Column(name = "end_credit_date", nullable = false)
    private Date endCreditDate;
    @Column(name = "credit_state", nullable = false, length = 10)
    private Boolean creditState;
    @Column(name = "is_accepted", nullable = false, length = 10)
    private Boolean isAccepted;
    @Column(name = "is_risk_accepted", nullable = false, length = 10)
    private Boolean isRiskAccepted;
    @Column(name = "percent", nullable = false, length = 10)
    private byte percent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", nullable = false)
    private CustomerHistoryEntity customerHistoryEntity;


    public CreditEntity(final Banks bankName, final String loanAmount, final String paymentPerMonth, final Date startCreditDate,
                        final Date endCreditDate, final byte percent, final CustomerHistoryEntity customerHistoryEntity,
                        final CreditType creditType, final Boolean isAccepted, final Boolean creditState,
                        final Boolean isRiskAccepted) {

        this.bankName = Banks.valueOf(bankName.toString());
        this.loanAmount = loanAmount;
        this.paymentPerMonth = paymentPerMonth;
        this.startCreditDate = startCreditDate;
        this.endCreditDate = endCreditDate;
        this.percent = percent;
        this.customerHistoryEntity = customerHistoryEntity;
        this.creditType = CreditType.valueOf(creditType.toString());
        this.creditState = creditState;
        this.isAccepted = isAccepted;
       this.isRiskAccepted = isRiskAccepted;

    }

    public CreditEntity(final CreditModel creditModel) {
        this.creditType = CreditType.valueOf(creditModel.getCreditType().toString());
        this.bankName = Banks.valueOf(creditModel.getBankName().toString());
        this.loanAmount = creditModel.getLoanAmount();
        this.startCreditDate = creditModel.getStartCreditDate();
        this.endCreditDate = creditModel.getEndCreditDate();
        this.percent = creditModel.getPercent();
        this.creditState = creditModel.getCreditState();
        this.isAccepted = creditModel.getAccepted();
        this.paymentPerMonth = creditModel.getPaymentPerMonth();
        this.isRiskAccepted = creditModel.getRiskAccepted();
    }

    public CreditEntity() {
    }


    public String getBankName() {
        return bankName.toString();
    }

    public void setBankName(final Banks bankName) {
        this.bankName = Banks.valueOf(bankName.toString());
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(final String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPaymentPerMonth() {
        return paymentPerMonth;
    }

    public void setPaymentPerMonth(final String paymentPerMonth) {
        this.paymentPerMonth = paymentPerMonth;
    }

    public Date getStartCreditDate() {
        return startCreditDate;
    }

    public void setStartCreditDate(final Date startCreditDate) {
        this.startCreditDate = startCreditDate;
    }

    public Date getEndCreditDate() {
        return endCreditDate;
    }

    public String getCreditType() {
        return creditType.toString();
    }

    public void setCreditType(Banks creditType) {
        this.creditType = CreditType.valueOf(creditType.toString());
    }

    public void setEndCreditDate(final Date endCreditDate) {
        this.endCreditDate = endCreditDate;
    }

    public byte getPercent() {
        return percent;
    }

    public void setPercent(final byte percent) {
        this.percent = percent;
    }

    public CustomerHistoryEntity getCustomerHistoryEntity() {
        return customerHistoryEntity;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public void setCustomerHistoryEntity(final CustomerHistoryEntity customerHistoryEntity) {
        this.customerHistoryEntity = customerHistoryEntity;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public Boolean getCreditState() {
        return creditState;
    }

    public void setCreditState(Boolean creditState) {
        this.creditState = creditState;
    }

    public Boolean getRiskAccepted() {
        return isRiskAccepted;
    }

    public void setRiskAccepted(Boolean riskAccepted) {
        isRiskAccepted = riskAccepted;
    }
}
