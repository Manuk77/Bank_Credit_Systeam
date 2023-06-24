package com.example.bank.service;
import com.example.bank.bank_model.filter_customer_info.FilterCustomerInfo;
import com.example.bank.bank_model.portfolio.CustomerWithMathModelFields;
import com.example.bank.bank_model.portfolio.Portfolio;
import com.example.bank.bank_model.risk_calculating.CreditHistoryType;
import com.example.bank.bank_model.risk_calculating.RiskCalculating;
import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerModel;
import com.example.bank.customer.dto.CustomerModelFiltered;
import com.example.bank.customer.response.CustomerResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private static int countOfRequests;
    private static final List<CustomerModel> customerModels = new ArrayList<>();

    private final RiskCalculating riskCalculating = new RiskCalculating();
    private final Long capitalOfBank = 40_000_000L;
    private final List<FilterCustomerInfo> filterCustomerInfos = new ArrayList<>();

    public List<CustomerModel> calculateRisks(final CustomerModel customerModel, final Optional<CustomerResponse> customerOp, final String creditTime) {
        if (customerOp.map(customerResponse -> getAnswer(customerModel, customerResponse, creditTime))
                .orElseGet(() -> getAnswerElse(customerModel, creditTime))) {
            countOfRequests++;
            if (countOfRequests == 10) {
                countOfRequests = 0;
                Portfolio portfolio = new Portfolio(customerModels, getMathModelFields(riskCalculating.getPD()));
                return portfolio.getOptimalCustomersList();

                // portfolio optimization
            }
            return new ArrayList<>();
        }

        return null;
    }


    private boolean getAnswerElse(final CustomerModel customerModel, final String creditTime) {

        filterCustomerInfos.add(new FilterCustomerInfo(getModelForRanking(customerModel, creditTime)));
        //rankedModels.add(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        riskCalculating.setRankedModels(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        if (riskCalculating.allRiskCalculations()) {
            customerModels.add(customerModel);
            return true;
        }


        return false;
    }

    private boolean getAnswer(final CustomerModel customerModel,
                              final CustomerResponse customerResponse,
                              final String creditTime) {

        CustomerModel customerModel1 = new CustomerModel(customerResponse);
        customerModel1.getCustomerHistoryModel().getCreditModels().add(customerModel.getCustomerHistoryModel().getCreditModels().get(
                customerModel.getCustomerHistoryModel().getCreditModels().size() - 1));
//        customerModel.getCustomerHistoryModel().setCreditScore(Short.valueOf(customerResponse.customerHistoryResponse().creditScore()));
        filterCustomerInfos.add(new FilterCustomerInfo(getModelForRanking(customerModel1, creditTime)));
        //rankedModels.add(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        RiskCalculating riskCalculating = new RiskCalculating(FilterCustomerInfo.filterCustomerRequest().rankedModel());


        if (riskCalculating.allRiskCalculations()) {

            // customerResponse converts or maps to customerRequest
            // eli ban ka avelcnelu
            customerModels.add(new CustomerModel(customerResponse));
            return true;
        }

        return false;
    }

    private List<CustomerWithMathModelFields> getMathModelFields(final List<Double> PD) {
        final double LGD = 0.5;
        final double Rf = 0.05;

        List<CustomerWithMathModelFields> customersFields = new ArrayList<>();
        double[] Ri = new double[PD.size()];
        double[] Pi = new double[PD.size()];
        double[] Wi = new double[PD.size()];
        double[] Sigma = new double[PD.size()];

        for (int i = 0; i < PD.size(); ++i) {
            Pi[i] = PD.get(i)*((1 - LGD) + (1 - PD.get(i))) / (1 + Rf) *
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

    private CustomerModelFiltered getModelForRanking(final CustomerModel customerModel, final String creditTIme) {
        return new CustomerModelFiltered(
                Integer.valueOf(customerModel.getCustomerInfoModel().getAge()),
                Integer.valueOf(customerModel.getWorkingPlaceModel().getSalary()),
                customerModel.getCustomerHistoryModel().getCreditModels().get(
                        customerModel.getCustomerHistoryModel().getCreditModels().size() - 1).getCreditType(),
                getCreditHistoryType(customerModel.getCustomerHistoryModel().getCreditScore()),
                Integer.valueOf(customerModel.getCustomerHistoryModel().getCreditModels().get(
                        customerModel.getCustomerHistoryModel().getCreditModels().size() - 1).getLoanAmount()),
                Integer.valueOf(creditTIme)
        );
    }


    private static CreditHistoryType getCreditHistoryType(final int creditScore) {
        int score = creditScore;
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
}
