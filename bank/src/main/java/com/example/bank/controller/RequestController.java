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
    private static List<CustomerRequest> customerRequests = new ArrayList<>();

    private Integer capitalOfBank;
    @PostMapping("/risk")
    public @ResponseBody Boolean getInfo(@RequestBody @NonNull final CustomerRequestFiltered customerRequestFiltered) {
        final String path = "http://localhost:8080/Customer/getInfo/" + customerRequestFiltered.passportRequest().passportNumber();

        RestTemplate rt = new RestTemplate();
        Optional<CustomerResponse> customerOp = Optional.ofNullable(rt.getForObject(path, CustomerResponse.class));
        if (customerOp.map(customerResponse -> getAnswer(customerRequestFiltered, customerResponse))
                .orElseGet(() -> getAnswerElse(customerRequestFiltered))) {
            countOfRequests++;
            if (countOfRequests == 10) {
                //Portfolio portfolio  = new Portfolio(customerRequests,)
            }
        }

        return false;
    }



    private boolean getAnswerElse(final CustomerRequestFiltered customerRequestFiltered) {

        List<RankedModel> rankedModels = new ArrayList<>();
        List<FilterCustomerInfo> filterCustomerInfos = new ArrayList<>();
        List<CreditRequest> creditRequests = new ArrayList<>();

        filterCustomerInfos.add(new  FilterCustomerInfo(customerRequestFiltered));
        rankedModels.add(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        RiskCalculating riskCalculating = new RiskCalculating(rankedModels);

        for (final Boolean b: riskCalculating.allRiskCalculations()) {
            //System.out.println(b);

            creditRequests.add( new CreditRequest(customerRequestFiltered.creditRequest().bankName(),
                    customerRequestFiltered.creditRequest().loanAmount(),
                    customerRequestFiltered.creditRequest().creditType(),
                    "0",
                    LocalDate.now().toString(),
                    LocalDate.now().plusMonths(Integer.
                            parseInt(customerRequestFiltered.creditRequest().creditTime())).toString(),
                    "false",
                    "false",
                    "0"));

            if (b) {
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

            }

            return b;
        }

        return false;
    }

    private boolean getAnswer(final CustomerRequestFiltered customerRequestFiltered,
                              final CustomerResponse customerResponse) {


        List<RankedModel> rankedModels = new ArrayList<>();
        List<FilterCustomerInfo> filterCustomerInfos = new ArrayList<>();

        filterCustomerInfos.add(new FilterCustomerInfo(customerRequestFiltered, customerResponse));
        rankedModels.add(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        RiskCalculating riskCalculating = new RiskCalculating(rankedModels);
        for (final Boolean b: riskCalculating.allRiskCalculations()) {

            if (b) {

                // customerResponse converts or maps to customerRequest
                // eli ban ka avelcnelu
                customerRequests.add(CustomerRequest.getFromResponse(customerResponse));

            }
            return b;
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

        for (int i = 0; i < PD.size(); ++i) {
            Pi[i] = PD.get(i)*((1 - LGD) + (1 - PD.get(i))) / (1 + Rf) *
                   Integer.parseInt(customerRequests.get(i).customerHistoryRequest().creditRequest().
                            get(customerRequests.get(i).customerHistoryRequest().creditRequest().size()).loanAmount());

            Ri[i] = (Integer.parseInt(customerRequests.get(i).customerHistoryRequest().creditRequest().
                    get(customerRequests.get(i).customerHistoryRequest().creditRequest().size()).loanAmount()) / Pi[i]) - 1;

            Wi[i] = (double) Integer.parseInt(customerRequests.get(i).customerHistoryRequest().creditRequest().
                    get(customerRequests.get(i).customerHistoryRequest().creditRequest().size()).loanAmount()) / capitalOfBank;

        }

        return null;
    }

    private boolean postRejectedRequests(final CustomerRequest customerRequest) {

        final String urlRejected = ""; // url of postMethod where is going to be passed CustomerRequest
        RestTemplate postRequest = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomerRequest> customerRequestHttpEntity = new HttpEntity<>(customerRequest, httpHeaders);
        ResponseEntity<CustomerRequest> response = postRequest.exchange(urlRejected, HttpMethod.POST, customerRequestHttpEntity, CustomerRequest.class);
        return response.getHeaders().isEmpty();

    }

}
