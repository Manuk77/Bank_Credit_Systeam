package com.example.bank.bank_model.risk_calculating;




import java.util.ArrayList;
import java.util.List;

public class RiskCalculating {
    private List<RankedModel> rankedModels;

    public RiskCalculating(final List<RankedModel> rankedModels) {
        this.rankedModels = rankedModels;
    }

    public RiskCalculating() {
    }

    public void setRankedModels(final List<RankedModel> rankedModels) {
        this.rankedModels = rankedModels;
    }

    public List<RankedModel> getRankedModels() {
        return rankedModels;
    }

    public List<Boolean> allRiskCalculations() {
        return riskCounting();
    }

    /**
     * calculating of y
     *
     * @return List
     */
    private List<Boolean> riskCounting() {
        final float betta0 = 0.039f;
        final float betta1 = -0.16f;
        final float betta2 = 0.0003f;
        final float betta3 = 0.024f;
        final float betta4 = 0.109f;
        final float betta5 = 0.108f;
        final float betta6 = 0.307f;

        List<Double> y = new ArrayList<>();

        for (final RankedModel rm : rankedModels) {
            y.add((double) (betta0 + betta1 * rm.getX1() + betta2 * rm.getX2() + betta3 * rm.getX3() +
                    betta4 * rm.getX4() + betta5 * rm.getX5() + betta6 * rm.getX6()));
        }
        return booleanListOfCustomers(y);
    }

    /**
     * @param listY is list of double y's
     * @return list of booleans
     */
    private List<Boolean> booleanListOfCustomers(final List<Double> listY) {
        List<Boolean> booleanY = new ArrayList<>();
        for (final Double y : listY) {
            booleanY.add(logisticModel(y));
        }
        return booleanY;
    }

    /**
     * this logistic method calculates y with this function
     *
     * @param y is double 1 > number
     * @return true if (1 / (1 + Math.pow(Math.E, y)))  <  DPt, else false
     * DPt is probability of default set by the bank
     */
    private boolean logisticModel(final Double y) {
        final double DPt = 0.824;
        double x = 1 - (1 / (1 + Math.pow(Math.E, y)));
        System.out.println("x = " + x);
        return x < DPt;
    }



}