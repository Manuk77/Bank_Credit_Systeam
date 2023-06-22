package com.example.bank.bank_model.filter_customer_info;

import com.example.bank.bank_model.risk_calculating.CreditHistoryType;
import com.example.bank.bank_model.risk_calculating.ModelOfRanking;
import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.response.CreditResponse;
import com.example.bank.customer.response.CustomerResponse;
import java.util.ArrayList;
import java.util.List;

public class FilterCustomerInfo {

    private static CustomerRequestFiltered customerRequestFiltered;
    private static CustomerResponse customerResponse;


    public FilterCustomerInfo(final CustomerRequestFiltered customerRequestFiltered) {
        FilterCustomerInfo.customerRequestFiltered = customerRequestFiltered;
    }

    public FilterCustomerInfo(final CustomerRequestFiltered customerRequestFiltered,
                              final CustomerResponse customerResponse) {
        FilterCustomerInfo.customerRequestFiltered = customerRequestFiltered;
        FilterCustomerInfo.customerResponse = customerResponse;
    }

    public static ModelOfRanking filterCustomerResponse() {

        List<CreditModel> creditModels = new ArrayList<>();
        for (final CreditResponse cr : customerResponse.customerHistoryResponse().creditResponse()) {
            creditModels.add(new CreditModel(cr));
        }

        return new ModelOfRanking(
                Integer.valueOf(customerResponse.customerInfoResponse().age()),
                customerIncome(creditModels, customerRequestFiltered.workingPlaceRequest().salary()),
                CreditType.valueOf(customerRequestFiltered.creditRequest().creditType()),
                getCreditHistoryType(customerResponse.customerHistoryResponse().creditScore()),
                Integer.valueOf(customerRequestFiltered.creditRequest().loanAmount()),
                Integer.valueOf(customerRequestFiltered.creditRequest().creditTime()));

    }

    public static  ModelOfRanking filterCustomerRequest() {

        return new ModelOfRanking(
                Integer.valueOf(customerRequestFiltered.customerInfoRequest().age()),
                Integer.valueOf(customerRequestFiltered.workingPlaceRequest().salary()),
                CreditType.valueOf(customerRequestFiltered.creditRequest().creditType()),
                getCreditHistoryType("600"),
                Integer.valueOf(customerRequestFiltered.creditRequest().loanAmount()),
                Integer.valueOf(customerRequestFiltered.creditRequest().creditTime()));
    }

    /**
     * this method changes customer credit score to the enum types
     * @param creditScore is customers credit score which can be 5 types(POOR, FAIR, GOOD, VERY_GOOD, EXCEPTIONAL)
     * @return creditHistoryType
     */
    private static CreditHistoryType getCreditHistoryType(final String creditScore) {
        int score = Integer.parseInt(creditScore);
        if (score > 299 && score < 580)
            return CreditHistoryType.POOR;
        if (score > 579 && score < 670)
            return CreditHistoryType.FAIR;
        if (score > 669 && score < 740)
            return CreditHistoryType.GOOD;
        if (score > 739 && score < 800)
            return CreditHistoryType.VERY_GOOD;
        if (score > 799 && score < 851)
            return CreditHistoryType.EXCEPTIONAL;
        return CreditHistoryType.POOR;
    }

    /**
     * this method calculates how much money will stay from salary for new credit
     * @param creditModels credit's list from credit history
     * @param salary of the customer
     * @return different of the (salary -= all credits paymentPerMonth)
     */
    private static Integer customerIncome(final List<CreditModel> creditModels, final String salary) {

        int salary1 = Integer.parseInt(salary);
        for (final CreditModel cm: creditModels) {
            if (cm.getCreditState()) {
                salary1 -= Integer.parseInt(cm.getPaymentPerMonth());
            }
        }
        return salary1;
    }




    public void setCustomerRequest(CustomerRequestFiltered customerRequestFiltered) {
        FilterCustomerInfo.customerRequestFiltered = customerRequestFiltered;
    }


    public void setCustomerResponse(CustomerResponse customerResponse) {
        FilterCustomerInfo.customerResponse = customerResponse;
    }
}
