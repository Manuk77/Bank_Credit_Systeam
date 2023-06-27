package com.example.bank.bank_model.portfolio;
/**
 * The CustomerWithMathModelFields class represents a customer with mathematical model fields used in portfolio analysis.
 * It holds information about the customer's sigma (standard deviation of returns), r (expected return), and w (portfolio weight).
 */
public class CustomerWithMathModelFields {
    private double sigma; // Standard deviation of returns (volatility of returns)
    private double r; // Expected return
    private double w; // Portfolio weight


    /**
     * Constructs a new instance of the CustomerWithMathModelFields class with the specified values for sigma, r, and w.
     *
     * @param sigma The standard deviation of returns.
     * @param r     The expected return.
     * @param w     The portfolio weight.
     */
    public CustomerWithMathModelFields(final double sigma, final double r, final double w) {
        this.sigma = sigma;
        this.r = r;
        this.w = w;
    }

    /**
     * Gets the standard deviation of returns (sigma).
     *
     * @return The standard deviation of returns.
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * Sets the standard deviation of returns (sigma).
     *
     * @param sigma The standard deviation of returns to be set.
     */
    public void setSigma(final double sigma) {
        this.sigma = sigma;
    }

    /**
     * Gets the expected return (r).
     *
     * @return The expected return.
     */
    public double getR() {
        return r;
    }

    /**
     * Sets the expected return (r).
     *
     * @param r The expected return to be set.
     */
    public void setR(final double r) {
        this.r = r;
    }

    /**
     * Gets the portfolio weight (w).
     *
     * @return The portfolio weight.
     */
    public double getW() {
        return w;
    }

    /**
     * Sets the portfolio weight (w).
     *
     * @param w The portfolio weight to be set.
     */
    public void setW(final double w) {
        this.w = w;
    }
}

