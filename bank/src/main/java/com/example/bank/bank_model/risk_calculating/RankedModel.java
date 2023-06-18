package com.example.bank.bank_model.risk_calculating;



public class RankedModel {
    private final int X1;  // customer age
    private final int X2; // customer income
    private final int X3; // credit type
    private final int X4; // credit history type
    private final int X5; //  loan amount
    private final int X6; // credit time

    public RankedModel(final int X1, final int X2, final int X3,
                       final int X4, final int X5, final int X6) {
        this.X1 = X1;
        this.X2 = X2;
        this.X3 = X3;
        this.X4 = X4;
        this.X5 = X5;
        this.X6 = X6;

    }

    public int getX1() {
        return X1;
    }

    public int getX2() {
        return X2;
    }

    public int getX3() {
        return X3;
    }

    public int getX4() {
        return X4;
    }

    public int getX5() {
        return X5;
    }

    public int getX6() {
        return X6;
    }

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
