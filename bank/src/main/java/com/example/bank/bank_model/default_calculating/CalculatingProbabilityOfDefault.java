package com.example.bank.bank_model.default_calculating;

import java.util.ArrayList;
import java.util.List;


/**
 * The `RiskCalculating` class is responsible for performing risk calculations based on a `RankedModel` and credit time.
 * It uses logistic regression to calculate the risk probability for customers and determines if the risk exceeds the threshold set by the bank.
 */
public class CalculatingProbabilityOfDefault {
    private RankedModel rankedModel;
    private String creditTime;
    public static List<Double> PD = new ArrayList<>(); // Probability of Default (PD) values
    public static List<String> creditTimes = new ArrayList<>();

    /**
     * Constructs a new empty `RiskCalculating` object.
     */
    public CalculatingProbabilityOfDefault() {
    }

    /**
     * Constructs a new `RiskCalculating` object with the specified `RankedModel`.
     *
     * @param rankedModel The `RankedModel` containing the rankings for different risk factors.
     */
    public CalculatingProbabilityOfDefault(final RankedModel rankedModel) {
        this.rankedModel = rankedModel;
    }

    /**
     * Sets the `RankedModel` and credit time for risk calculations.
     *
     * @param rankedModel The `RankedModel` containing the rankings for different risk factors.
     * @param creditTime  The credit time for risk calculations.
     */
    public void setRankedModels(final RankedModel rankedModel, final String creditTime) {
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
     * Calculates the risk using logistic regression.
     *
     * @return `true` if the risk exceeds the threshold, `false` otherwise.
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


        double y;
        List<Double> Y = new ArrayList<>();

        y = betta0 + betta1 * rankedModel.getX1() + betta2 * rankedModel.getX2() + betta3 * rankedModel.getX3() +
                betta4 * rankedModel.getX4() + betta5 * rankedModel.getX5() + betta6 * rankedModel.getX6();
        Y.add(y);
        if (booleanListOfCustomers(y)) {
            creditTimes.add(this.creditTime);
            return true;
        }
        return false;

    }

    /**
     * Determines if the calculated risk exceeds the threshold.
     *
     * @param y The calculated risk value.
     * @return `true` if the risk exceeds the threshold, `false` otherwise.
     */
    private Boolean booleanListOfCustomers(final Double y) {
         return logisticModel(y);
    }

    /**
     * Calculates the risk using the logistic model.
     *
     * @param y The calculated risk value.
     * @return `true` if the calculated risk is greater than the threshold (DPt), `false` otherwise.
     *         DPt is the probability of default set by the bank.
     */
    private boolean logisticModel(final Double y) {
        final double DPt = 0.5;
        double x = 1 - (1 / (1 + Math.pow(Math.E, -y)));
//        System.out.println("PDi = " + x);
        if (x < DPt) {
            PD.add(x);
            return true;
        }
        return false;
    }

    public static void newListPD() {
        PD = new ArrayList<>();
    }

    public static List<String> getCreditTimes() {
        return creditTimes;
    }
}