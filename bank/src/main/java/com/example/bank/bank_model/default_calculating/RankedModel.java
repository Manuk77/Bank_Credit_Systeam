package com.example.bank.bank_model.default_calculating;



/**
 * The `RankedModel` class represents a model that holds the rankings for different factors used in risk calculation.
 * It contains rankings for customer age, customer income, credit type, credit history type, loan amount, and credit time.
 */
public class RankedModel {
    private final int X1;  // customer age
    private final int X2;  // customer income
    private final int X3;  // credit type
    private final int X4;  // credit history type
    private final int X5;  // loan amount
    private final int X6;  // credit time

    /**
     * Constructs a new `RankedModel` object with the specified rankings for each factor.
     *
     * @param X1 The ranking for customer age.
     * @param X2 The ranking for customer income.
     * @param X3 The ranking for credit type.
     * @param X4 The ranking for credit history type.
     * @param X5 The ranking for loan amount.
     * @param X6 The ranking for credit time.
     */
    public RankedModel(final int X1, final int X2, final int X3,
                       final int X4, final int X5, final int X6) {
        this.X1 = X1;
        this.X2 = X2;
        this.X3 = X3;
        this.X4 = X4;
        this.X5 = X5;
        this.X6 = X6;
    }

    /**
     * Retrieves the ranking for customer age.
     *
     * @return The ranking for customer age.
     */
    public int getX1() {
        return X1;
    }

    /**
     * Retrieves the ranking for customer income.
     *
     * @return The ranking for customer income.
     */
    public int getX2() {
        return X2;
    }

    /**
     * Retrieves the ranking for credit type.
     *
     * @return The ranking for credit type.
     */
    public int getX3() {
        return X3;
    }

    /**
     * Retrieves the ranking for credit history type.
     *
     * @return The ranking for credit history type.
     */
    public int getX4() {
        return X4;
    }

    /**
     * Retrieves the ranking for loan amount.
     *
     * @return The ranking for loan amount.
     */
    public int getX5() {
        return X5;
    }

    /**
     * Retrieves the ranking for credit time.
     *
     * @return The ranking for credit time.
     */
    public int getX6() {
        return X6;
    }

    /**
     * Returns a string representation of the `RankedModel` object.
     *
     * @return A string representation of the `RankedModel` object.
     */
    @Override
    public String toString() {
        return "RankedModel{" +
                "X1=" + X1 +
                ", X2=" + X2 +
                ", X3=" + X3 +
                ", X4=" + X4 +
                ", X5=" + X5 +
                ", X6=" + X6 +
                '}';
    }
}
