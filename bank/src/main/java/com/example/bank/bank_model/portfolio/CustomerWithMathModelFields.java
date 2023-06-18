package com.example.bank.bank_model.portfolio;

public class CustomerWithMathModelFields {
    private float sigma; // եկամտաբերության դիսպերսիա (եկամտաբերւթյան միջին քառակուսաին շեղում)
    private int r; // եկամտաբերություն
    private float w; // կշրաին գործակից

    public CustomerWithMathModelFields() {
    }

    public CustomerWithMathModelFields(final float sigma, final int r, final float w) {
        this.sigma = sigma;
        this.r = r;
        this.w = w;
    }

    public float getSigma() {
        return sigma;
    }

    public void setSigma(final float sigma) {
        this.sigma = sigma;
    }

    public int getR() {
        return r;
    }

    public void setR(final int r) {
        this.r = r;
    }

    public float getW() {
        return w;
    }

    public void setW(final float w) {
        this.w = w;
    }

}
