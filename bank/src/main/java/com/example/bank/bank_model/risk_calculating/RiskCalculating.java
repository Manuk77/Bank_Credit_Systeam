package com.example.bank.bank_model.risk_calculating;

import java.util.ArrayList;
import java.util.List;

public class RiskCalculating {
    private RankedModel rankedModel;
    public List<Double> PD = new ArrayList<>(); // դեֆոլտի հավանականությունը

    public RiskCalculating(final RankedModel rankedModel) {
        this.rankedModel = rankedModel;
    }

    public RiskCalculating() {
    }

    public void setRankedModels(final RankedModel rankedModel) {
        this.rankedModel = rankedModel;
    }

    public RankedModel getRankedModels() {
        return rankedModel;
    }

    public Boolean allRiskCalculations() {
        return riskCounting();
    }

    public List<Double> getPD() {
        return PD;
    }


    /**
     * calculating of y
     *
     * @return List
     */
    private Boolean riskCounting() {
        final float betta0 = -0.04798f;
        final float betta1 = -0.00553f;
        final float betta2 = 0.096932f;
        final float betta3 = 0.042873f;
        final float betta4 = 0.151273f;
        final float betta5 = -0.01447f;
        final float betta6 = -0.00565f;

        List<Double> Y = new ArrayList<>();
        double y;

        y = (betta0 + betta1 * rankedModel.getX1() + betta2 * rankedModel.getX2() + betta3 * rankedModel.getX3() +
                betta4 * rankedModel.getX4() + betta5 * rankedModel.getX5() + betta6 * rankedModel.getX6());
        Y.add(y);
        return booleanListOfCustomers(y);
    }


    private Boolean booleanListOfCustomers(final Double y) {
         return logisticModel(y);
    }

    /**
     * this logistic method calculates y with this function
     *
     * @param y is double 1 > number
     * @return true if (1 / (1 + Math.pow(Math.E, y)))  <  DPt, else false
     * DPt is probability of default set by the bank
     */
    private boolean logisticModel(final Double y) {
        final double DPt = 0.6;
        double x = 1 - (1 / (1 + Math.pow(Math.E, y)));
        System.out.println("PDi = " + x);
        if (x > DPt) {
            PD.add(x);
            return true;
        }
        return false;
    }


}