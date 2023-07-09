package com.example.bank.bank_model.portfolio;
import com.example.bank.customer.dto.CustomerModel;


import java.util.*;


/**
 * The Portfolio class represents a portfolio of customer loans and provides methods to calculate optimal loan options based on given constraints.
 * It contains a list of customer models, a list of customer math models, and variables to track the minimum value of L and the acceptable loan list.
 * The class includes methods to set and retrieve the customer models and customer math models.
 * It also provides methods to calculate the optimal loans by generating all possible binary lists and applying the capacity constraint calculation.
 * The `optimalLoans` method selects the customers whose binary value is 1 and adds them to the list of optimal loans,
 * while customers with a binary value of 0 are added to the `notIncludedCustomerModelLoans` list.
 * Additionally, the class includes methods to retrieve the optimal loans list and the list of not included customer model loans.
 */
public class Portfolio {
    private List<CustomerModel> customerModels;
    private static List<CustomerModel> notIncludedCustomerModelLoans = new ArrayList<>();
    private List<CustomerWithMathModelFields> customersMath;
    public static List<Integer> y;
    private static Double Sum = Double.MAX_VALUE;
    private List<Integer> acceptableLoan;
    private static Double R;

    /**
     * Constructs an empty Portfolio object.
     */
    public Portfolio() {
    }

    /**
     * Constructs a Portfolio object with the specified customer models and customer math models.
     *
     * @param customerModels The list of customer models.
     * @param customersMath  The list of customer math models.
     */

    public Portfolio(final List<CustomerModel> customerModels,
                     final List<CustomerWithMathModelFields> customersMath) {


        this.customerModels = customerModels;
        this.customersMath = customersMath;
    }

    /**
     * Retrieves the list of customer models in the portfolio.
     *
     * @return The list of customer models.
     */
    public List<CustomerModel> getCustomerRequests() {
        return customerModels;
    }

    /**
     * Sets the list of customer models in the portfolio.
     *
     * @param customerModels The list of customer models.
     */
    public void setCustomerRequests(final List<CustomerModel> customerModels) {
        this.customerModels = customerModels;
    }

    /**
     * Retrieves the list of customer math models in the portfolio.
     *
     * @return The list of customer math models.
     */
    public List<CustomerWithMathModelFields> getCustomersMath() {
        return customersMath;
    }

    /**
     * Sets the list of customer math models in the portfolio.
     *
     * @param customersMath The list of customer math models.
     */
    public void setCustomersMath(final List<CustomerWithMathModelFields> customersMath) {
        this.customersMath = customersMath;
    }

    /**
     * Applies the capacity constraint calculation based on the given binary list of loan options.
     * The method checks if the constraints are satisfied: Σ(ri * ni * wi) >= R and Σ(wi * ni) <= 1.
     * If the constraints are satisfied, it calculates the value of L = Σ(σi^2 * ni^2 * wi^2) and updates the minimum value of L.
     *
     * @param listN The binary list of loan options. Each element represents whether a loan option is selected (1) or not (0).
     */
    private void capacityConstraint(final List<Integer> listN) {

        double sum = 0.0;
        double sum1 = 0L;
        double R = 0.07;


        // Calculate the sum of wi * ni and ri * ni * wi
        for (int i = 0; i < customersMath.size(); ++i) {
            sum += customersMath.get(i).getW() * listN.get(i);
            sum1 += (customersMath.get(i).getR() * listN.get(i) * customersMath.get(i).getW());
        }
        // Check if the constraints are satisfied
        if (sum <= 1 && sum1 >= R) {
            double summ = 0.0;
            // Calculate the value of L = Σ(σi^2 * ni^2 * wi^2)
            for (int i = 0; i < customersMath.size(); ++i) {
                summ +=  (Math.pow(customersMath.get(i).getSigma(), 2) *
                        Math.pow(listN.get(i), 2) *
                        Math.pow(customersMath.get(i).getW(), 2));

            }
            // Update the minimum value of L and the acceptable loan list
            if (summ < Sum) {
                Portfolio.R = sum1;
                Sum = summ;
                acceptableLoan = listN;
                y = listN;
            }

        }


    }

    /**
     * Calculates the optimal loans for customers based on the given constraints.
     * It invokes the allPossibleOptions() method to generate all possible binary lists.
     * Then, it iterates through the acceptableLoan list and selects the customers whose corresponding binary value is 1,
     * adding them to the list of optimal loans.
     * Additionally, it adds the customers whose binary value is 0 to the notIncludedCustomerModelLoans list.
     * Finally, it prints the count of acceptable customer loans and returns the list of optimal loans.
     *
     * @return The list of customer models representing the optimal loans.
     */
    private List<CustomerModel> optimalLoans() {
        List<CustomerModel> optimalLoans = new ArrayList<>();
        allPossibleOptions();
        int j = 0;
        for (int i = 0; i < acceptableLoan.size(); ++i) {

            if (acceptableLoan.get(i) == 1) {
                j++;
                optimalLoans.add(customerModels.get(i));
            }else {
                notIncludedCustomerModelLoans.add(customerModels.get(i));
            }

        }
        System.out.println("acceptable customer loan count is -> " + j);
        System.out.println("portfeli ekamtaberutyun -> " + R);
        return optimalLoans;
    }


    /**
     * Calculates all possible options of a binary list.
     * It generates one possible option at a time and passes it to the capacityConstraint() method,
     * which checks if the option conforms to the constraints. If it does, it calculates the value of L = Σ(σi^2 * ni^2 * wi^2)
     * and adds it to the list of L values.
     * After calculating all possible options, the method finds the minimum element in the list of L values,
     * and retrieves the binary list of N associated with that minimum value.
     * Example of generating binary lists:
     * [1, 0, 0, 0, ..., 0]
     * [0, 1, 0, 0, ..., 0]
     *  .  .  .  .  ...  .
     *  .  .  .  .  ...  .
     *  .  .  .  .  ...  .
     * [1, 1, 1, 1, ..., 1]
     */
    private void allPossibleOptions() {
        int k = customersMath.size(); // Length of the list
        int totalOptions = (int) Math.pow(2, k) - 1;
        for (int i = 1; i <= totalOptions; i++) {
            List<Integer> option = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                int bit = (i >> j) & 1;
                option.add(bit);
            }
            capacityConstraint(option);
        }
    }

    /**
     * Retrieves the list of customer models representing the optimal loans based on the given constraints.
     *
     * @return The list of customer models representing the optimal loans.
     */
    public List<CustomerModel> getOptimalCustomersList() {
        return optimalLoans();
    }

    /**
     * Retrieves the list of customer model loans that are not included in the optimal loans based on the given constraints.
     *
     * @return The list of customer models not included in the optimal loans.
     */
    public static List<CustomerModel> getNotIncludedCustomerModelLoans() {
        return notIncludedCustomerModelLoans;
    }

    public static List<Integer> getY() {
        return y;
    }

    public static Double getSum() {
        return Sum;
    }

    public static Double getR() {
        return R;
    }
}
