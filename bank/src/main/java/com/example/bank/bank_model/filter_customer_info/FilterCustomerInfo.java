package com.example.bank.bank_model.filter_customer_info;

import com.example.bank.bank_model.default_calculating.ModelOfRanking;
import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerHistoryModel;
import com.example.bank.customer.dto.CustomerModelFiltered;

import java.util.ArrayList;
import java.util.List;

/**
 * The FilterCustomerInfo class is responsible for filtering and processing customer information
 * based on certain criteria and business rules.
 */
public class FilterCustomerInfo {

    private static  CustomerModelFiltered customerModelFiltered;
    private static CustomerHistoryModel customerHistoryModel;

    /**
     * Constructs a new instance of the FilterCustomerInfo class with the specified filtered customer model.
     *
     * @param customerModelFiltered The filtered customer model to be processed.
     */

    public FilterCustomerInfo(final CustomerModelFiltered customerModelFiltered) {
        FilterCustomerInfo.customerModelFiltered = customerModelFiltered;
    }

    /**
     * Filters the customer model and calculates the ranking score based on the provided criteria.
     *
     * @return A ModelOfRanking object representing the filtered and ranked customer model.
     */

    public static ModelOfRanking filterCustomerModel() {

        List<CreditModel> creditModels = new ArrayList<>(customerHistoryModel.getCreditModels());
        customerModelFiltered.setCustomerIncome(customerIncome(creditModels, customerModelFiltered.getCustomerIncome()));
        return new ModelOfRanking(customerModelFiltered);

    }

    /**
     * Filters the customer model and returns the original model if the filtering is not required.
     *
     * @return A ModelOfRanking object representing the customer model without any filtering applied.
     */
    public static  ModelOfRanking filterCustomerModelOrElse() {
        return new ModelOfRanking(customerModelFiltered);
    }



    /**
     * Calculates the remaining income of the customer after deducting the monthly payments of all credits.
     *
     * @param creditModels The list of credits from the customer's credit history.
     * @param salary       The salary of the customer.
     * @return The remaining income of the customer after deducting the credit payments.
     */
    private static Integer customerIncome(final List<CreditModel> creditModels, final Integer salary) {

        double salary1 = salary;
        for (final CreditModel cm: creditModels) {
            if (cm.getCreditState()) {
                salary1 -= Double.parseDouble(cm.getPaymentPerMonth());
            }
        }
        return (int) salary1;
    }


    /**
     * Sets the customer request in the FilterCustomerInfo class.
     *
     * @param customerModelFiltered The filtered customer model to be set as the customer request.
     */

    public void setCustomerRequest(final CustomerModelFiltered customerModelFiltered) {
        FilterCustomerInfo.customerModelFiltered = customerModelFiltered;
    }

    /**
     * Sets the customer history model in the FilterCustomerInfo class.
     *
     * @param customerHistoryModel The customer history model to be set.
     */

    public static void setCustomerHistoryModel(final CustomerHistoryModel customerHistoryModel) {
        FilterCustomerInfo.customerHistoryModel = customerHistoryModel;
    }

    public CustomerModelFiltered getCustomerModelFiltered() {
        return customerModelFiltered;
    }
}
