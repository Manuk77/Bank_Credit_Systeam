package com.example.bank.bank_model.default_calculating;

import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.dto.CustomerModelFiltered;


/**
 * The `ModelOfRanking` class represents a model used for ranking customers based on various factors related to their credit information.
 * It contains fields for customer age, customer income, credit type, credit history type, loan amount, and credit time.
 */
public class ModelOfRanking {
    private final  Integer customerAge;
    private final Integer customerIncome;
    private  CreditType creditType;
    private final CreditHistoryType creditHistoryType;
    private Integer loanAmount;
    private Integer creditTime;


    /**
     * Constructs a new `ModelOfRanking` object with the specified field values.
     *
     * @param customerAge       The age of the customer.
     * @param customerIncome    The income of the customer.
     * @param creditType        The type of credit.
     * @param creditHistoryType The credit history type.
     * @param loanAmount        The loan amount.
     * @param creditTime        The credit time.
     */
    public ModelOfRanking(final Integer customerAge, final Integer customerIncome, final CreditType creditType,
                          final CreditHistoryType creditHistoryType, final Integer loanAmount, final Integer creditTime) {

        this.customerAge = customerAge;
        this.customerIncome = customerIncome;
        this.creditType = creditType;
        this.creditHistoryType = creditHistoryType;
        this.loanAmount = loanAmount;
        this.creditTime = creditTime;

    }

    /**
     * Constructs a new `ModelOfRanking` object from a `CustomerModelFiltered` object.
     * The field values are extracted from the `CustomerModelFiltered` object.
     *
     * @param customerModelFiltered The `CustomerModelFiltered` object to construct from.
     */
    public ModelOfRanking(final CustomerModelFiltered customerModelFiltered) {
        this.creditType = customerModelFiltered.getCreditType();
        this.creditHistoryType = customerModelFiltered.getCreditHistoryType();
        this.loanAmount = customerModelFiltered.getLoanAmount();
        this.creditTime = customerModelFiltered.getCreditTime();
        this.customerAge = customerModelFiltered.getCustomerAge();
        this.customerIncome = customerModelFiltered.getCustomerIncome();
    }

    /**
     * Retrieves the customer's age.
     *
     * @return The customer's age.
     */
    public Integer getCustomerAge() {
        return customerAge;
    }

    /**
     * Retrieves the customer's income.
     *
     * @return The customer's income.
     */
    public Integer getCustomerIncome() {
        return customerIncome;
    }

    /**
     * Retrieves the credit type.
     *
     * @return The credit type.
     */
    public CreditType getCreditType() {
        return creditType;
    }

    /**
     * Retrieves the credit history type.
     *
     * @return The credit history type.
     */
    public CreditHistoryType getCreditHistoryType() {
        return creditHistoryType;
    }

    /**
     * Retrieves the loan amount.
     *
     * @return The loan amount.
     */
    public Integer getLoanAmount() {
        return loanAmount;
    }

    /**
     * Retrieves the credit time.
     *
     * @return The credit time.
     */
    public Integer getCreditTime() {
        return creditTime;
    }

    /**
     * Sets the credit type.
     *
     * @param creditType The credit type to set.
     */
    public void setCreditType(final CreditType creditType) {
        this.creditType = creditType;
    }

    /**
     * Sets the loan amount.
     *
     * @param loanAmount The loan amount to set.
     */
    public void setLoanAmount(final Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * Sets the credit time.
     *
     * @param creditTime The credit time to set.
     */
    public void setCreditTime(final Integer creditTime) {
        this.creditTime = creditTime;
    }


    /**
     * Calculates the rankings for each field based on their values.
     *
     * @return A `RankedModel` object containing the rankings for each field.
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


    /**
     * Calculates the ranking for the customer's age.
     *
     * @return The ranking for the customer's age.
     */
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

    /**
     * Calculates the ranking for the customer's income.
     *
     * @return The ranking for the customer's income.
     */
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

    /**
     * Calculates the ranking for the credit type.
     *
     * @return The ranking for the credit type.
     */
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

    /**
     * Calculates the ranking for the credit history type.
     *
     * @return The ranking for the credit history type.
     */
    private int rankingCreditHistoryType() {
        return switch (creditHistoryType) {
            case POOR -> 1;
            case FAIR -> 2;
            case GOOD -> 3;
            case VERY_GOOD -> 4;
            case EXCEPTIONAL -> 5;
        };
    }

    /**
     * Calculates the ranking for the loan amount.
     *
     * @return The ranking for the loan amount.
     */
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

    /**
     * Calculates the ranking for the credit time.
     *
     * @return The ranking for the credit time.
     */
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
