package com.example.bank.controller;
import com.example.bank.customer.bank.Banks;
import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerModel;
import com.example.bank.customer.response.CustomerResponse;
import com.example.bank.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(final RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/risk")
    public @ResponseBody Boolean getInfo(@RequestBody final CustomerRequestFiltered customerRequestFiltered) {
        final String creditTime = customerRequestFiltered.creditRequest().creditTime();
        CustomerModel customerModel = getFromCustomerRequestFiltered(customerRequestFiltered);
        final String path = "http://localhost:8080/Customer/getInfo/" + customerRequestFiltered.passportRequest().passportNumber();
        RestTemplate rt = new RestTemplate();
        Optional<CustomerResponse> customerOp = Optional.ofNullable(rt.getForObject(path, CustomerResponse.class));
        List<CustomerModel> customerModels = requestService.calculateRisks(customerModel, customerOp, creditTime);
        if (customerModels == null)
            return true;
        return postAcceptedRequests(customerModels);
    }



    private boolean postAcceptedRequests(final List<CustomerModel> customerModels) {

        for (final CustomerModel customerModel: customerModels) {
            CustomerRequest customerRequest = CustomerRequest.getFromModel(customerModel);
            final String urlRejected = "http://localhost:8080/Customer/saveCustomer"; // url of postMethod where is going to be passed CustomerRequest
            RestTemplate postRequest = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CustomerRequest> customerRequestHttpEntity = new HttpEntity<>(customerRequest, httpHeaders);
            ResponseEntity<Boolean> response = postRequest.exchange(urlRejected, HttpMethod.POST, customerRequestHttpEntity, Boolean.class);
            if (!Boolean.TRUE.equals(response.getBody()))
                return false;
        }
        return true;
    }

    private CustomerModel getFromCustomerRequestFiltered(final CustomerRequestFiltered customerRequestFiltered) {
        List<CreditModel> creditModels = new ArrayList<>();
        creditModels.add(new CreditModel(
                CreditType.valueOf(customerRequestFiltered.creditRequest().creditType()),
                Banks.valueOf(customerRequestFiltered.creditRequest().bankName()),
                customerRequestFiltered.creditRequest().loanAmount(),
                Date.valueOf(LocalDate.now()),
                Date.valueOf(
                        LocalDate.now().plusMonths(
                                Integer.parseInt(
                                        customerRequestFiltered.creditRequest().creditTime())
                        )
                ),
                "0",
                (byte) 0,
                false,
                false
        ));
        return new CustomerModel(customerRequestFiltered,creditModels);
    }


}
