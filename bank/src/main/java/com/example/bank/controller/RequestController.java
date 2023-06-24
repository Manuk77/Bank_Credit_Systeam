package com.example.bank.controller;

import com.example.bank.customer.bank.Banks;
import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerModel;
import com.example.bank.customer.response.CustomerResponse;
import com.example.bank.mailmessage.EmailService;
import com.example.bank.service.RequestService;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final BankController bankController;

    private final EmailService emailService;

    @Autowired
    public RequestController(RequestService requestService, BankController bankController, EmailService emailService) {
        this.requestService = requestService;
        this.bankController = bankController;
        this.emailService = emailService;
    }

    /**
     *
     * @param customerRequestFiltered
     * @return
     */

    @PostMapping("/risk")
    public @ResponseBody Boolean getInfo(@RequestBody final CustomerRequestFiltered customerRequestFiltered) {
        final String creditTime = customerRequestFiltered.creditRequest().creditTime();
        CustomerModel customerModel = getFromCustomerRequestFiltered(customerRequestFiltered);
        final String path = "http://localhost:8080/Customer/getInfo/" + customerRequestFiltered.passportRequest().passportNumber();
        RestTemplate rt = new RestTemplate();
        Optional<CustomerResponse> customerOp = Optional.ofNullable(rt.getForObject(path, CustomerResponse.class));
        List<CustomerModel> customerModels = requestService.calculateRisks(customerModel, customerOp, creditTime);
        if (customerModels == null) {
            return false;
        }
        for (CustomerModel cm : customerModels) {
            if (postAcceptedRequests(cm)) {
                try {
                    emailService.sendEmailWithAttachment(cm);
                } catch (MessagingException | DocumentException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }

    /**
     *
     * @param customerModel
     * @return
     */

    private boolean postAcceptedRequests(final CustomerModel customerModel) {
        CustomerRequest customerRequest = CustomerRequest.getFromModel(customerModel);
        bankController.saveInfoAcceptedCustomers(customerRequest);
        final String urlRejected = "http://localhost:8080/Customer/saveCustomer"; // url of postMethod where is going to be passed CustomerRequest
        RestTemplate postRequest = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomerRequest> customerRequestHttpEntity = new HttpEntity<>(customerRequest, httpHeaders);
        ResponseEntity<Boolean> response = postRequest.exchange(urlRejected, HttpMethod.POST, customerRequestHttpEntity, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    /**
     *
     * @param customerRequestFiltered
     * @return
     */
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
        return new CustomerModel(customerRequestFiltered, creditModels);
    }
}
