package com.example.bank.bank_model.portfolio;

import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import org.springframework.lang.NonNull;


import java.util.*;


public class Portfolio {
    private List<CustomerRequest> customerRequests;
    private List<CustomerWithMathModelFields> customersMath;

    private final Map<Double, List<Integer>> mapL = new HashMap<>();
    private final List<Double> L = new ArrayList<>();
    private List<Integer> acceptableLoan;

    public Portfolio() {
    }

    public Portfolio(final List<CustomerRequest> customerRequests,
                     final List<CustomerWithMathModelFields> customersMath) {


        this.customerRequests = customerRequests;
        this.customersMath = customersMath;
    }


    public List<CustomerRequest> getCustomerRequests() {
        return customerRequests;
    }

    public void setCustomerRequests(final List<CustomerRequest> customerRequests) {
        this.customerRequests = customerRequests;
    }

    public List<CustomerWithMathModelFields> getCustomersMath() {
        return customersMath;
    }

    public void setCustomersMath(final List<CustomerWithMathModelFields> customersMath) {
        this.customersMath = customersMath;
    }

    /**
     * L = Σ(σi^2 * ni^2 * wi^2) -> min
     * Σ(ri * ni * wi) >= R
     * Σ(wi * ni) <= 1
     * ni ∈ {0, 1}, i = 1, ..., N
     * possible values of list {0, 1}
     */
    private void capacityConstraint(final List<Integer> listN) {


        double sum = 0.0;
        double sum1 = 0L;
//        double r = 0.0;
//        for (CustomerWithMathModelFields customerWithMathModelFields : customersMath) {
//            r += customerWithMathModelFields.getR();
//        }

        double R = 0.07;
        for (int i = 0; i < customersMath.size(); ++i) {
            sum += customersMath.get(i).getW() * listN.get(i);
            sum1 += (customersMath.get(i).getR() * listN.get(i) * customersMath.get(i).getW());
        }
        if (sum <= 1 && sum1 >= R) {
            double summ = 0.0;
            for (int i = 0; i < customersMath.size(); ++i) {
                summ +=  (Math.pow(customersMath.get(i).getSigma(), 2) *
                        Math.pow(listN.get(i), 2) *
                        Math.pow(customersMath.get(i).getW(), 2));

            }
            L.add(summ);
            mapL.put(summ, listN);

        }


    }

    /**
     * optimal loans customer lists
     *
     * @return acceptable customerRequest loans list
     */
    private @NonNull List<CustomerRequest> optimalLoans() {
        List<CustomerRequest> optimalLoans = new ArrayList<>();
        acceptableLoan = allPossibleOptions();
        System.out.println("boolean list size" + acceptableLoan.size());
        for (int i = 0; i < acceptableLoan.size(); ++i) {
            if (acceptableLoan.get(i) == 1)
                optimalLoans.add(customerRequests.get(i));

        }
        return optimalLoans;
    }


    /**
     * this method calculates all possible options of binary list:
     * it calculates one possible option and passes to capacityConstraint() witch
     * checks whether conforms to the constraints, if it does it calculates L = Σ(σi^2 * ni^2 * wi^2)
     * and adds in the list L:
     * when all possible options are calculated, finds in list L min element,
     * and gets from with that key the binary list of N:
     * ex. [1, 0, 0, 0, ..., 0],
     *     [0, 1, 0, 0, ..., 0]
     *      .  .  .  .  ...  .
     *      .  .  .  .  ...  .
     *      .  .  .  .  ...  .
     *     [1, 1, 1, 1, ..., 1]
     */
    private List<Integer> allPossibleOptions() {
        int k = customersMath.size(); // Length of the list
        int totalOptions = (int) Math.pow(2, k) - 1;
        List<Integer> option = new ArrayList<>();

        // Create a two-dimensional list to store the options
        for (int i = 1; i <= totalOptions; i++) {


            for (int j = 0; j < k; j++) {
                int bit = (i >> j) & 1;
                option.add(bit);
            }

            capacityConstraint(option);
            if (i == 1023)
                break;
            option.clear();
        }

        return mapL.get(Collections.min(L));
       // System.out.println(acceptableLoan);

    }

   private void printOptimalLoans() {
        for (CustomerRequest customerRequest: optimalLoans()) {
            System.out.println(customerRequest);
        }
   }

    public List<CustomerRequest> getOptimalCustomersList() {
        return optimalLoans();
    }
}
