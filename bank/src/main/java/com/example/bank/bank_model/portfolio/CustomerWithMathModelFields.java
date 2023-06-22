package com.example.bank.bank_model.portfolio;

public class CustomerWithMathModelFields {
    private double sigma; // եկամտաբերության դիսպերսիա (եկամտաբերւթյան միջին քառակուսային շեղում)
    private double r; // եկամտաբերություն
    private double w; // կշռային գործակից

    public CustomerWithMathModelFields() {
    }

    public CustomerWithMathModelFields(final double sigma, final double r, final double w) {
        this.sigma = sigma;
        this.r = r;
        this.w = w;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(final double sigma) {
        this.sigma = sigma;
    }

    public double getR() {
        return r;
    }

    public void setR(final double r) {
        this.r = r;
    }

    public double getW() {
        return w;
    }

    public void setW(final double w) {
        this.w = w;
    }

}
