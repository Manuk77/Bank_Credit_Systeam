package com.example.bank.bank_model.risk_calculating;

import java.util.ArrayList;
import java.util.List;

public class RiskCalculating {
    private RankedModel rankedModel;
    private String creditTime;
    public List<Double> PD = new ArrayList<>(); // դեֆոլտի հավանականությունը
    public static List<String> creditTimes = new ArrayList<>();

    public RiskCalculating(final RankedModel rankedModel) {
        this.rankedModel = rankedModel;
    }

    public RiskCalculating() {
    }

    public void setRankedModels(final RankedModel rankedModel, String creditTime) {
        this.rankedModel = rankedModel;
        this.creditTime = creditTime;
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


        final double betta0 = -0.04798;
        final double betta1 = -0.00553;
        final double betta2 = 0.096932;
        final double betta3 = 0.042873;
        final double betta4 = 0.151273;
        final double betta5 = -0.01447;
        final double betta6 = -0.00565;

//        final double betta0 = 0.333811373850653;
//        final double betta1 = 0.00624585073685336;
//        final double betta2 = 0.073415350552171;
//        final double betta3 = 0.000116718158751398;
//        final double betta4 = 0.0707717296999949;
//        final double betta5 = -0.0779933503359887;
//        final double betta6 = 0.101723516747402;

        List<Double> Y = new ArrayList<>();
        double y;

        y = betta0 + betta1 * rankedModel.getX1() + betta2 * rankedModel.getX2() + betta3 * rankedModel.getX3() +
                betta4 * rankedModel.getX4() + betta5 * rankedModel.getX5() + betta6 * rankedModel.getX6();
        Y.add(y);
        if (booleanListOfCustomers(y)) {
            creditTimes.add(this.creditTime);
            return true;
        }
        return false;

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
        final double DPt = 0.59;
        double x = 1 - (1 / (1 + Math.pow(Math.E, y)));
        System.out.println("PDi = " + x);
        if (x > DPt) {
            PD.add(x);
            return true;
        }
        return false;
    }



}