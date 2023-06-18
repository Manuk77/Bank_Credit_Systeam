package com.example.bank.controller;

import com.example.bank.bank_model.filter_customer_info.FilterCustomerInfo;
import com.example.bank.bank_model.risk_calculating.RankedModel;
import com.example.bank.bank_model.risk_calculating.RiskCalculating;
import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import com.example.bank.customer.response.CustomerResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
public class RequestController {

    @PostMapping("/risk")
    public @ResponseBody Boolean getInfo(@RequestBody @NonNull final CustomerRequestFiltered customerRequestFiltered) {
        final String path = "http://localhost:8080/Customer/getInfo/" + customerRequestFiltered.passportRequest().passport_number();

        RestTemplate rt = new RestTemplate();
        Optional<CustomerResponse> customerOp = Optional.ofNullable(rt.getForObject(path, CustomerResponse.class));
        return customerOp.map(customerResponse -> getAnswer(customerRequestFiltered, customerResponse))
                .orElseGet(() -> getAnswerElse(customerRequestFiltered));


    }



    private boolean getAnswerElse(final CustomerRequestFiltered customerRequestFiltered) {

        List<RankedModel> rankedModels = new ArrayList<>();
        List<FilterCustomerInfo> filterCustomerInfos = new ArrayList<>();

        filterCustomerInfos.add(new  FilterCustomerInfo(customerRequestFiltered));
        rankedModels.add(FilterCustomerInfo.filterCustomerRequest().rankedModel());
        RiskCalculating riskCalculating = new RiskCalculating(rankedModels);
        for (final Boolean b: riskCalculating.allRiskCalculations()) {
            System.out.println(b);
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
            System.out.println(b);
            return b;
        }
        return false;
    }

}
