package com.example.bank.service;

import com.example.bank.bank_model.filter_customer_info.FilterCustomerInfo;
import com.example.bank.bank_model.portfolio.CustomerWithMathModelFields;
import com.example.bank.bank_model.portfolio.Portfolio;
import com.example.bank.bank_model.default_calculating.CreditHistoryType;
import com.example.bank.bank_model.default_calculating.CalculatingProbabilityOfDefault;
import com.example.bank.controller.RequestController;
import com.example.bank.custome_exceptions.DuplicateCustomerRequestException;
import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerModel;
import com.example.bank.customer.dto.CustomerModelFiltered;
import com.example.bank.customer.dto.ResultCustomerInfoModel;
import com.example.bank.customer.response.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The RequestService class is responsible for calculating risks for customer models and performing portfolio optimization.
 * It provides methods to calculate risks based on customer responses and credit time, as well as methods to add missing
 * fields of credit and perform portfolio optimization.
 * The class maintains a count of requests and a list of customer models. It also uses an instance of the RiskCalculating
 * class for risk calculations and a constant value for the capital of the bank.
 * The main method in this class is calculateRisks, which takes a customer model, an optional customer response,
 * and a credit time as parameters. It calculates the risks for the given customer model based on the provided customer
 * response and credit time. If a customer response is available, it calls the getAnswer method with the customer model,
 * customer response, and credit time to calculate the risks. If no customer response is available, it calls the getAnswerElse
 * method with the customer model and credit time to calculate the risks. If the calculated risks meet certain conditions,
 * it performs portfolio optimization by creating a Portfolio object with the current customer models and the math model
 * fields for risk calculating. It then retrieves the optimal customers list from the portfolio and adds any missing fields
 * of credit based on the defined credit times. Additionally, if the count of requests reaches 10, it resets the count
 * and performs portfolio optimization.
 * The class also includes private helper methods such as getAnswer, getAnswerElse, getMathModelFields,
 * getModelForRanking, getCreditHistoryType, and addingMissingFieldsOfCredit to facilitate the risk calculations,
 * creation of filtered customer models, calculation of mathematical model fields, determination of credit history type,
 * and addition of missing fields of credit.
 * This class follows the Singleton pattern and should be accessed through its public methods.
 */

@Service
public class RequestService {
    private static int countOfRequests;
    private static List<CustomerModel> customerModels = new ArrayList<>();
    private final List<List<CustomerModel>> allCustomerModels = new ArrayList<>();
    public static List<ResultCustomerInfoModel> resultCustomerInfoModels = new ArrayList<>();
    public static CalculatingProbabilityOfDefault calculatingProbabilityOfDefault = new CalculatingProbabilityOfDefault();
    public static Long capitalOfBank = 40_000_000L;
    private static List<FilterCustomerInfo> filterCustomerInfos = new ArrayList<>();

    /**
     * Calculates the risks for the given customer model based on the provided customer response and credit time.
     * If a customer response is available, it calls the 'getAnswer' method with the customer model,
     * customer response, and credit time to calculate the risks.
     * If no customer response is available, it calls the 'getAnswerElse' method with the customer model and credit time
     * to calculate the risks.
     * If the calculated risks meet certain conditions, it performs portfolio optimization by creating a Portfolio object
     * with the current customer models and the math model fields for risk calculating. It then retrieves the optimal
     * customers list from the portfolio and adds any missing fields of credit based on the defined credit times.
     * Additionally, if the count of requests reaches 10, it resets the count and performs portfolio optimization.
     *
     * @param customerModel The customer model for which risks need to be calculated.
     * @param customerOp    An optional customer response, if available.
     * @param creditTime    The credit time for the calculations.
     * @return A list of CustomerModel objects if risks are calculated successfully and meet the conditions, or null otherwise
     */
    public List<CustomerModel> calculateRisks(final CustomerModel customerModel, final Optional<CustomerResponse> customerOp, final String creditTime) {
        if (!customerModels.isEmpty())
            for (final CustomerModel cm : customerModels) {
                if (cm.getPassportModel().equals(customerModel.getPassportModel()))
                    if (cm.getCustomerHistoryModel().getCreditModels().get(0).
                            equals(customerModel.getCustomerHistoryModel().getCreditModels().get(0)))
                        throw new DuplicateCustomerRequestException("Duplicate Customer Request ");

            }

        if (customerOp.map(customerResponse -> getAnswer(customerModel, customerResponse, creditTime))
                .orElseGet(() -> getAnswerElse(customerModel, creditTime))) {
            countOfRequests++;
            if (countOfRequests == 10) {
                countOfRequests = 0;
                Portfolio portfolio = new Portfolio(customerModels, getMathModelFields(calculatingProbabilityOfDefault.getPD()));
                List<CustomerModel> customerModels1 = null;
                try {
                   customerModels1  = portfolio.getOptimalCustomersList();
                }catch (NullPointerException e) {
                    allCustomerModels.add(customerModels);
                    resultCustomerInfoModels.get(0).setL(Portfolio.getSum());
                    resultCustomerInfoModels.get(0).setR(Portfolio.getR());
                    customerModels.clear();
                    filterCustomerInfos.clear();
                    throw new NullPointerException("portfolios profitability is less than 0.07");
                }
                if (customerModels1 != null) {
                    allCustomerModels.add(customerModels);
                    resultCustomerInfoModels.get(0).setL(Portfolio.getSum());
                    resultCustomerInfoModels.get(0).setR(Portfolio.getR());
                    customerModels.clear();
                    filterCustomerInfos.clear();
                    return addingMissingFieldsOfCredit(customerModels1, CalculatingProbabilityOfDefault.creditTimes);
                }
                // portfolio optimization
            }
            return new ArrayList<>();
        }
        return null;
    }

    /**
     * Calculates the risks for the given customer model and credit time when a customer response is not available.
     * It creates a FilterCustomerInfo object with the customer model and credit time,
     * and adds it to the filterCustomerInfos list.
     * Then, it sets the ranked models for risk calculating by calling the 'setRankedModels' method of the riskCalculating object
     * with the filtered customer model from FilterCustomerInfo and the credit time.
     * If all the risk calculations are successful according to the riskCalculating object,
     * it adds the customer model to the customerModels list and returns true.
     * Otherwise, it returns false.
     *
     * @param customerModel The customer model for which risks need to be calculated.
     * @param creditTime    The credit time for the calculations.
     * @return True if the risks are calculated successfully and meet the conditions, false otherwise.
     */
    private boolean getAnswerElse(final CustomerModel customerModel, final String creditTime) {
        FilterCustomerInfo filterCustomerInfo = new FilterCustomerInfo(getModelForRanking(customerModel, creditTime));
        filterCustomerInfos.add(filterCustomerInfo);
        resultCustomerInfoModels.add(new ResultCustomerInfoModel(filterCustomerInfo.getCustomerModelFiltered()));

        calculatingProbabilityOfDefault.setRankedModels(FilterCustomerInfo.filterCustomerModelOrElse().rankedModel(), creditTime);
        if (calculatingProbabilityOfDefault.allRiskCalculations()) {
            customerModels.add(customerModel);
            return true;
        }
        return false;
    }

    /**
     * Calculates the risks for the given customer model and customer response based on the provided credit time.
     * It creates a new CustomerModel (customerModel1) from the customerResponse to ensure consistent salary values.
     * If the salary of customerModel1's workingPlaceModel is different from customerModel's workingPlaceModel,
     * it updates the salary values of both workingPlaceModel and customerHistoryModel in customerModel1
     * to match the salary of customerModel's workingPlaceModel.
     * It then adds the latest credit model from customerModel's customerHistoryModel to customerModel1's customerHistoryModel.
     * Next, it creates a FilterCustomerInfo object with the filtered customer model (customerModel1)
     * and the provided creditTime, and adds it to the filterCustomerInfos list.
     * It sets the customerHistoryModel of FilterCustomerInfo using customerModel1's customerHistoryModel.
     * It creates a RiskCalculating object using the ranked model from FilterCustomerInfo's filterCustomerModel method.
     * If all the risk calculations are successful according to the riskCalculating object,
     * it adds a new CustomerModel object created from the customerResponse to the customerModels list and returns true.
     * Otherwise, it returns false.
     *
     * @param customerModel    The original customer model for comparison and reference.
     * @param customerResponse The customer response used for creating the filtered customer model.
     * @param creditTime       The credit time for the risk calculations.
     * @return True if the risks are calculated successfully and meet the conditions, false otherwise.
     */

    private boolean getAnswer(final CustomerModel customerModel,
                              final CustomerResponse customerResponse,
                              final String creditTime) {

        for (CreditModel cm : new CustomerModel(customerResponse).getCustomerHistoryModel().getCreditModels()) {
            if (cm.equals(customerModel.getCustomerHistoryModel().getCreditModels().get(0)))
                throw new DuplicateCustomerRequestException("DuplicateCustomerRequestException: The Request Already Exist");
        }
        CustomerModel customerModel1 = new CustomerModel(customerResponse);
        if (Integer.parseInt(customerModel1.getWorkingPlaceModel().getSalary()) != Integer.parseInt(customerModel.getWorkingPlaceModel().getSalary())) {
            customerModel1.getWorkingPlaceModel().setSalary(customerModel.getWorkingPlaceModel().getSalary());
            customerModel1.getCustomerHistoryModel().setSalary(customerModel.getWorkingPlaceModel().getSalary());
        }

        customerModel1.getCustomerHistoryModel().getCreditModels().add(customerModel.getCustomerHistoryModel().getCreditModels().get(
                customerModel.getCustomerHistoryModel().getCreditModels().size() - 1));
        FilterCustomerInfo filterCustomerInfo = new FilterCustomerInfo(getModelForRanking(customerModel1, creditTime));

        filterCustomerInfos.add(filterCustomerInfo);
        resultCustomerInfoModels.add(new ResultCustomerInfoModel(filterCustomerInfo.getCustomerModelFiltered()));
        FilterCustomerInfo.setCustomerHistoryModel(customerModel1.getCustomerHistoryModel());

        CalculatingProbabilityOfDefault calculatingProbabilityOfDefault = new CalculatingProbabilityOfDefault(FilterCustomerInfo.filterCustomerModel().rankedModel());


        if (calculatingProbabilityOfDefault.allRiskCalculations()) {
            // customerResponse converts or maps to customerRequest
            // eli ban ka avelcnelu
            customerModels.add(customerModel1);
            return true;
        }
        return false;
    }

    /**
     * Calculates the mathematical model fields for each customer based on the provided Probability of Default (PD) values.
     * It initializes the Loss Given Default (LGD) value as 0.5 and the Risk-free Rate (Rf) value as 0.05.
     * Creates a list of CustomerWithMathModelFields objects to store the calculated fields for each customer.
     * Initializes arrays Ri, Pi, Wi, and Sigma with sizes equal to the number of PD values.
     * Iterates over the PD values and calculates the Pi, Ri, Wi, and Sigma values for each customer.
     * Pi is calculated as PD[i] * ((1 - LGD) + (1 - PD[i])) / (1 + Rf) * LoanAmount,
     * where LoanAmount is obtained from the latest credit model of the corresponding customer.
     * Ri is calculated as (LoanAmount / Pi[i]) - 1.
     * Wi is calculated as customerModels.forEach(customerModel1 -> {
     * if (customerModel1.equals(customerModel))
     * <p>
     * }) ;anAmount / capitalOfBank, where capitalOfBank is a constant.
     * Sigma is calculated using the PD, LGD, and Rf values according to the specified formula.
     * Creates a new CustomerWithMathModelFields object for each customer and adds it to the customersFields list.
     *
     * @param PD The list of Probability of Default (PD) values for each customer.
     * @return The list of CustomerWithMathModelFields objects containing the calculated mathematical model fields.
     */
    private List<CustomerWithMathModelFields> getMathModelFields(final List<Double> PD) {
        final double LGD = 0.5;
        final double Rf = 0.05;

        List<CustomerWithMathModelFields> customersFields = new ArrayList<>();
        double[] Ri = new double[PD.size()];
        double[] Pi = new double[PD.size()];
        double[] Wi = new double[PD.size()];
        double[] Sigma = new double[PD.size()];

        for (int i = 0; i < 10; ++i) {
            Pi[i] = PD.get(i) * ((1 - LGD) + (1 - PD.get(i))) / (1 + Rf) *
                    Double.parseDouble(customerModels.get(i).getCustomerHistoryModel().getCreditModels().
                            get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).getLoanAmount());

            Ri[i] = (Double.parseDouble(customerModels.get(i).getCustomerHistoryModel().getCreditModels().
                    get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).getLoanAmount()) / Pi[i]) - 1;

            Wi[i] = Double.parseDouble(customerModels.get(i).getCustomerHistoryModel().getCreditModels().
                    get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).getLoanAmount()) / capitalOfBank;

            Sigma[i] = Math.pow(1 + Rf, 2) * (Math.pow(LGD + PD.get(i), 2) * (1 - PD.get(i)) + Math.pow(LGD *
                    (PD.get(i) - 1), 2) * PD.get(i)) / Math.pow(1 - LGD * PD.get(i), 2);

            customersFields.add(new CustomerWithMathModelFields(Sigma[i], Ri[i], Wi[i]));
        }

        return customersFields;
    }

    /**
     * Creates a filtered version of the CustomerModel for ranking purposes.
     * Extracts specific attributes from the provided CustomerModel and constructs a new CustomerModelFiltered object.
     * The attributes used for filtering include:
     * Age: Extracts the age value from the CustomerInfoModel of the customerModel.
     * Salary: Extracts the salary value from the WorkingPlaceModel of the customerModel.
     * CreditType: Retrieves the credit type value from the latest CreditModel in the CreditModels list of the CustomerHistoryModel.
     * CreditHistoryType: Converts the credit score value from the CustomerHistoryModel into a CreditHistoryType enum value.
     * LoanAmount: Extracts the loan amount value from the latest CreditModel in the CreditModels list of the CustomerHistoryModel.
     * CreditTime: Converts the creditTime parameter to an Integer value.
     *
     * @param customerModel The original CustomerModel object to be filtered.
     * @param creditTime    The credit time value as a String.
     * @return The filtered CustomerModelFiltered object for ranking purposes.
     */

    private CustomerModelFiltered getModelForRanking(final CustomerModel customerModel, final String creditTime) {
        return new CustomerModelFiltered(
                customerModel.getCustomerInfoModel().getFirstName(),
                customerModel.getCustomerInfoModel().getLastName(),
                customerModel.getCustomerInfoModel().getAge(),
                Integer.valueOf(customerModel.getWorkingPlaceModel().getSalary()),
                customerModel.getCustomerHistoryModel().getCreditModels().get(
                        customerModel.getCustomerHistoryModel().getCreditModels().size() - 1).getCreditType(),
                getCreditHistoryType(customerModel.getCustomerHistoryModel().getCreditScore()),
                Integer.valueOf(customerModel.getCustomerHistoryModel().getCreditModels().get(
                        customerModel.getCustomerHistoryModel().getCreditModels().size() - 1).getLoanAmount()),
                Integer.valueOf(creditTime)
        );
    }


    /**
     * Determines the CreditHistoryType based on the provided credit score.
     * Evaluates the credit score against predefined score ranges to determine the appropriate CreditHistoryType.
     * The score ranges and their corresponding CreditHistoryType are as follows:
     * Score Range: 300-579, CreditHistoryType: POOR
     * Score Range: 580-669, CreditHistoryType: FAIR
     * Score Range: 670-739, CreditHistoryType: GOOD
     * Score Range: 740-799, CreditHistoryType: VERY_GOOD
     * Score Range: 800-850, CreditHistoryType: EXCEPTIONAL
     *
     * @param creditScore The credit score to be evaluated.
     * @return The corresponding CreditHistoryType based on the credit score.
     */
    private static CreditHistoryType getCreditHistoryType(final int creditScore) {
        if (creditScore > 299 && creditScore < 580)
            return CreditHistoryType.POOR;
        if (creditScore > 579 && creditScore < 670)
            return CreditHistoryType.FAIR;
        if (creditScore > 669 && creditScore < 740)
            return CreditHistoryType.GOOD;
        if (creditScore > 739 && creditScore < 800)
            return CreditHistoryType.VERY_GOOD;
        if (creditScore > 799 && creditScore < 851)
            return CreditHistoryType.EXCEPTIONAL;
        return CreditHistoryType.POOR;
    }


    /**
     * Adds missing fields of credit to the provided list of CustomerModels.
     * Calculates and adds the missing fields of credit for each CustomerModel in the list. The missing fields include
     * the payment per month, flag, percent, credit state, accepted status, and the active credit flag.
     *
     * @param customerModels The list of CustomerModels to which the missing fields of credit will be added.
     * @param creditTimes    The list of credit times corresponding to each CustomerModel.
     * @return The updated list of CustomerModels with the added missing fields of credit.
     */

    private List<CustomerModel> addingMissingFieldsOfCredit(final List<CustomerModel> customerModels,
                                                            final List<String> creditTimes) {
        int percent = 10;
        double paymentPerMonth;
        for (int i = 0; i < customerModels.size(); ++i) {
            paymentPerMonth = (Integer.parseInt(customerModels.get(i).getCustomerHistoryModel().getCreditModels().get(
                    customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).getLoanAmount()) * 1.1 / Integer.parseInt(creditTimes.get(i)));
            customerModels.get(i).getCustomerInfoModel().setFlag(true);
            customerModels.get(i).getCustomerHistoryModel().getCreditModels().get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).setPercent((byte) percent);
            customerModels.get(i).getCustomerHistoryModel().getCreditModels().get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).setPaymentPerMonth(String.valueOf(paymentPerMonth));
            customerModels.get(i).getCustomerHistoryModel().getCreditModels().get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).setCreditState(true);
            customerModels.get(i).getCustomerHistoryModel().getCreditModels().get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).setAccepted(true);
            customerModels.get(i).getCustomerHistoryModel().setHasActiveCredit(true);
            customerModels.get(i).getCustomerHistoryModel().getCreditModels().get(customerModels.get(i).getCustomerHistoryModel().getCreditModels().size() - 1).setRiskAccepted(true);
        }
        return customerModels;
    }

}
