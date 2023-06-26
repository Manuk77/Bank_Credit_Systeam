package com.example.bank.bank_model.risk_calculating;

import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.dto.CustomerModelFiltered;

public class ModelOfRanking {
    private final  Integer customerAge;
    private final Integer customerIncome;
    private  CreditType creditType;
    private final CreditHistoryType creditHistoryType;
    private Integer loanAmount;
    private Integer creditTime;


    public ModelOfRanking(final Integer customerAge, final Integer customerIncome, final CreditType creditType,
                          final CreditHistoryType creditHistoryType, final Integer loanAmount, final Integer creditTime) {

        this.customerAge = customerAge;
        this.customerIncome = customerIncome;
        this.creditType = creditType;
        this.creditHistoryType = creditHistoryType;
        this.loanAmount = loanAmount;
        this.creditTime = creditTime;

    }

    public ModelOfRanking(final CustomerModelFiltered customerModelFiltered) {
        this.creditType = customerModelFiltered.getCreditType();
        this.creditHistoryType = customerModelFiltered.getCreditHistoryType();
        this.loanAmount = customerModelFiltered.getLoanAmount();
        this.creditTime = customerModelFiltered.getCreditTime();
        this.customerAge = customerModelFiltered.getCustomerAge();
        this.customerIncome = customerModelFiltered.getCustomerIncome();
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

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;

    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setCreditTime(Integer creditTime) {
        this.creditTime = creditTime;
    }

    /**
     * ranks are between [1, 5] int numbers
     * this method ranks all 6 cols for calculating risks
     * @return RankedModel
     */
    public RankedModel rankedModel() {
        return new RankedModel(
                rankingAge(),
                rankingIncome(),
                rankingCreditType(),
                rankingCreditHistoryType(),
                rankingLoanAmount(),
                rankingCreditTime()
        );
    }


    private int rankingAge() {
       if (customerAge > 18 && customerAge < 22)
           return 1;
       if (customerAge > 21 && customerAge < 36)
           return 2;
       if (customerAge > 35 && customerAge < 46)
           return 3;
       if (customerAge > 45 && customerAge < 55)
           return 4;
       if (customerAge > 55 && customerAge < 100)
           return 5;
       return 0;
    }

    private int rankingIncome() {
        if (customerIncome > 59_999 && customerIncome < 151_000)
            return 1;
        if (customerIncome > 150_000 && customerIncome < 201_000)
            return 2;
        if (customerIncome > 200_000 && customerIncome < 301_000)
            return 3;
        if (customerIncome > 300_000 && customerIncome < 501_000)
            return 4;
        if (customerIncome > 500_000)
            return 5;
        return 0;
    }

    private int rankingCreditType() {
      return  switch (creditType) {
          case MORTGAGE -> 1;
          case CAR_PURCHASE_LOAN -> 2;
          case CONSUMER_LOAN -> 3;
          case GOLD_PAWN_LOAN -> 4;
          case CREDIT -> 5;
          case HOME_IMPROVEMENT_LOAN -> 6;
        };
    }

    private int rankingCreditHistoryType() {
        return switch (creditHistoryType) {
            case POOR -> 1;
            case FAIR -> 2;
            case GOOD -> 3;
            case VERY_GOOD -> 4;
            case EXCEPTIONAL -> 5;
        };
    }

    private int rankingLoanAmount() {
        if (loanAmount > 9999 && loanAmount < 300_001)
            return 1;
        if (loanAmount > 300_000 && loanAmount < 600_001)
            return 2;
        if (loanAmount > 600_000 && loanAmount < 1_500_001)
            return 3;
        if (loanAmount > 1_500_000 && loanAmount < 3_000_001)
            return 4;
        if (loanAmount > 3_000_000 && loanAmount < 6_000_001)
            return 5;
        if (loanAmount > 6_000_000 && loanAmount < 12_000_001)
            return 6;
        if (loanAmount > 12_000_000)
            return 7;
        return 0;
    }

    private int rankingCreditTime() {
        if (creditTime > 6 && creditTime < 19)
            return 1;
        if (creditTime > 18 && creditTime < 25)
            return 2;
        if (creditTime > 25 && creditTime < 61)
            return 3;
        if (creditTime > 60 && creditTime < 121)
            return 4;
        if (creditTime > 120)
            return 5;
        return 0;
    }


}
