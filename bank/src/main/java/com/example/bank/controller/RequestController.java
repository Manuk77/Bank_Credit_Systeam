package com.example.bank.controller;

import com.example.bank.bank_model.filter_customer_info.FilterCustomerInfo;
import com.example.bank.bank_model.portfolio.CustomerWithMathModelFields;
import com.example.bank.bank_model.portfolio.Portfolio;
import com.example.bank.bank_model.risk_calculating.RankedModel;
import com.example.bank.bank_model.risk_calculating.RiskCalculating;
import com.example.bank.customer.creating_requests.requests.CreditRequest;
import com.example.bank.customer.creating_requests.requests.CustomerHistoryRequest;
import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import com.example.bank.customer.response.CustomerResponse;
import com.example.bank.validator.annotation.NotNullEmptyBlankString;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
public class RequestController {
    private static int countOfRequests;
    private static final List<CustomerRequest> customerRequests = new ArrayList<>();

    private final RiskCalculating riskCalculating = new RiskCalculating();
    private final Long capitalOfBank = 40_000_000L;
    private final List<RankedModel> rankedModels = new ArrayList<>();
    private final List<FilterCustomerInfo> filterCustomerInfos = new ArrayList<>();

    @PostMapping("/risk")
    public @ResponseBody Boolean getInfo(@RequestBody @NonNull final CustomerRequestFiltered customerRequestFiltered) {
        final String path = "http://localhost:8080/Customer/getInfo/" + customerRequestFiltered.passportRequest().passportNumber();

        RestTemplate rt = new RestTemplate();
        Optional<CustomerResponse> customerOp = Optional.ofNullable(rt.getForObject(path, CustomerResponse.class));
        if (customerOp.map(customerResponse -> getAnswer(customerRequestFiltered, customerResponse))
                .orElseGet(() -> getAnswerElse(customerRequestFiltered))) {
            countOfRequests++;
            if (countOfRequests == 10) {
                Portfolio portfolio = new Portfolio(customerRequests, getMathModelFields(riskCalculating.getPD()));
                for (CustomerRequest customerRequest: portfolio.getOptimalCustomersList()) {
                    if (!postRejectedRequests(customerRequest)) {
                        return false;
                    }
                }
                countOfRequests = 0;
                // portfolio optimization
            }
            return true;
        }

        return false;
    }



    private boolean getAnswerElse(final CustomerRequestFiltered customerRequestFiltered) {

        List<CreditRequest> creditRequests = new ArrayList<>();
        filterCustomerInfos.add(new FilterCustomerInfo(customerRequestFiltered));
        //rankedModels.add(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        riskCalculating.setRankedModels(FilterCustomerInfo.filterCustomerRequest().rankedModel());


        creditRequests.add(new CreditRequest(customerRequestFiltered.creditRequest().bankName(),
                customerRequestFiltered.creditRequest().loanAmount(),
                customerRequestFiltered.creditRequest().creditType(),
                "0",
                LocalDate.now().toString(),
                LocalDate.now().plusMonths(Integer.
                        parseInt(customerRequestFiltered.creditRequest().creditTime())).toString(),
                "false",
                "false",
                "0"));
        //System.out.println(b);
        if (riskCalculating.allRiskCalculations()) {
            customerRequests.add(
                    new CustomerRequest(customerRequestFiltered.addressRequest(),
                            customerRequestFiltered.passportRequest(),
                            customerRequestFiltered.customerInfoRequest(),
                            new CustomerHistoryRequest(customerRequestFiltered.workingPlaceRequest().salary(),
                                    "false",
                                    "600",
                                    creditRequests),
                            customerRequestFiltered.workingPlaceRequest())
            );

            return true;
        }


        return false;
    }

    private boolean getAnswer(final CustomerRequestFiltered customerRequestFiltered,
                              final CustomerResponse customerResponse) {



        filterCustomerInfos.add(new FilterCustomerInfo(customerRequestFiltered, customerResponse));
        //rankedModels.add(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        RiskCalculating riskCalculating = new RiskCalculating(FilterCustomerInfo.filterCustomerRequest().rankedModel());


            if (riskCalculating.allRiskCalculations()) {

                // customerResponse converts or maps to customerRequest
                // eli ban ka avelcnelu
                customerRequests.add(CustomerRequest.getFromResponse(customerResponse));
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
                   Integer.parseInt(customerRequests.get(i).customerHistoryRequest().creditRequest().
                            get(customerRequests.get(i).customerHistoryRequest().creditRequest().size() - 1).loanAmount());

            Ri[i] = (Integer.parseInt(customerRequests.get(i).customerHistoryRequest().creditRequest().
                    get(customerRequests.get(i).customerHistoryRequest().creditRequest().size() - 1).loanAmount()) / Pi[i]) - 1;

            Wi[i] = (double) Integer.parseInt(customerRequests.get(i).customerHistoryRequest().creditRequest().
                    get(customerRequests.get(i).customerHistoryRequest().creditRequest().size() - 1).loanAmount()) / capitalOfBank;

            Sigma[i] = Math.pow(1 + Rf, 2) * (Math.pow(LGD + PD.get(i), 2) * (1 - PD.get(i)) + Math.pow(LGD *
                    (PD.get(i) - 1), 2) * PD.get(i)) / Math.pow(1 - LGD * PD.get(i), 2);

            customersFields.add(new CustomerWithMathModelFields(Sigma[i], Ri[i], Wi[i]));
        }

        return customersFields;
    }

    private boolean postRejectedRequests(final CustomerRequest customerRequest) {
        final String urlRejected = "http://localhost:8080/Customer/saveCustomer"; // url of postMethod where is going to be passed CustomerRequest
        RestTemplate postRequest = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomerRequest> customerRequestHttpEntity = new HttpEntity<>(customerRequest, httpHeaders);
        ResponseEntity<Boolean> response = postRequest.exchange(urlRejected, HttpMethod.POST, customerRequestHttpEntity, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());

    }


}
