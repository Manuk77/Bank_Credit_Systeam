package com.example.bank.controller;

import com.example.bank.bank_model.default_calculating.CalculatingProbabilityOfDefault;
import com.example.bank.bank_model.portfolio.Portfolio;
import com.example.bank.custome_exceptions.DuplicateCustomerRequestException;
import com.example.bank.customer.bank.Banks;
import com.example.bank.customer.bank.CreditType;
import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import com.example.bank.customer.dto.CreditModel;
import com.example.bank.customer.dto.CustomerModel;
import com.example.bank.customer.dto.ResultCustomerInfoModel;
import com.example.bank.customer.response.CustomerResponse;
import com.example.bank.mailmessage.EmailService;
import com.example.bank.service.BankService;
import com.example.bank.service.RequestService;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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

/**

 Controller class that handles HTTP requests related to customer requests.

 It maps the requests to appropriate methods and handles the corresponding logic.

 This class is responsible for managing customer requests and interacting with the RequestService, BankController,

 and EmailService components.
 */
@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private static BankController bankController;
    private final EmailService emailService;

    /**
     Constructs a new RequestController with the provided dependencies.
     @param requestService The RequestService component responsible for managing customer requests.
     @param bankController The BankController component responsible for handling bank-related operations.
     @param emailService The EmailService component responsible for sending email notifications.
     */
    @Autowired
    public RequestController(final RequestService requestService,
                             final BankController bankController,
                             final EmailService emailService) {
        this.requestService = requestService;
        RequestController.bankController = bankController;
        this.emailService = emailService;
    }

    /**
     * Retrieves customer information based on the provided filtered customer request.
     * Performs a POST request to an external service and calculates risks for the customer.
     * Based on the calculated risks, the customer information is either saved as rejected customers,
     * sent email notifications, or saved as accepted customers.
     *
     * @param customerRequestFiltered The filtered customer request containing the necessary information for retrieving customer data and calculating risks.
     * @return A list of CustomerResponse objects representing the saved or accepted customer information.
     */
    @PostMapping("/risk")
    public @ResponseBody List<CustomerResponse> getInfo(@Valid @RequestBody final CustomerRequestFiltered customerRequestFiltered) {

        final String creditTime = customerRequestFiltered.creditRequest().creditTime();
        CustomerModel customerModel = getFromCustomerRequestFiltered(customerRequestFiltered);
        final String path = "http://localhost:8080/Customer/getInfo/" + customerRequestFiltered.passportRequest().passportNumber();
        RestTemplate rt = new RestTemplate();
        Optional<CustomerResponse> customerOp = Optional.ofNullable(rt.getForObject(path, CustomerResponse.class));
        List<CustomerModel> customerModels;

        try {
           customerModels = requestService.calculateRisks(customerModel, customerOp, creditTime);
        }catch (DuplicateCustomerRequestException  e) {
            System.out.println(e.getMessage());
            return null;
        }catch (NullPointerException e) {
            Portfolio.getNotIncludedCustomerModelLoans().clear();
            RequestService.calculatingProbabilityOfDefault = new CalculatingProbabilityOfDefault();
            RequestService.resultCustomerInfoModels.clear();
            System.out.println(e.getMessage());
            return null;
        }
        List<CustomerRequest> customerRequests = new ArrayList<>();

        if (customerModels == null) {
            bankController.saveOrUpdateRejectedCustomers(CustomerRequest.getFromModel(customerModel));
            try {
                emailService.sendEmailCustomers("merjvel e",customerModel);
            } catch (MessagingException | DocumentException | IOException e) {
                throw new RuntimeException(e);
            }
            return List.of(CustomerResponse.getFromModel(customerModel));
        } else {
            for (CustomerModel cm : customerModels) {
                cm.getCustomerHistoryModel().getCreditModels().get(cm.getCustomerHistoryModel().getCreditModels().size() - 1).setRiskAccepted(true);
                customerRequests.add(CustomerRequest.getFromModel(cm));
                if (postAcceptedRequests(CustomerRequest.getFromModel(cm))) {
                    try {
                        emailService.sendEmailCustomers("hastatvel e",cm);
                    } catch (MessagingException | DocumentException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            for (CustomerModel cm : Portfolio.getNotIncludedCustomerModelLoans()) {
                cm.getCustomerHistoryModel().getCreditModels().get(cm.getCustomerHistoryModel().getCreditModels().size() - 1).setRiskAccepted(true);
                if(postNotAcceptedRequests(CustomerRequest.getFromModel(cm))) {
                    try {
                        emailService.sendEmailCustomers("merjvel e portfolioic", cm);
                    }catch (MessagingException | DocumentException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (RequestService.resultCustomerInfoModels.size() == 10) {
                Portfolio.getNotIncludedCustomerModelLoans().clear();
                RequestService.calculatingProbabilityOfDefault = new CalculatingProbabilityOfDefault();
                for (int i = 0; i < RequestService.resultCustomerInfoModels.size(); ++i) {
                    RequestService.resultCustomerInfoModels.get(i).setY(Portfolio.y.get(i));
                    if (i == 0) {
                        RequestService.resultCustomerInfoModels.get(i).setR(Portfolio.getR());
                        RequestService.resultCustomerInfoModels.get(i).setL(Portfolio.getSum());
                    }
                    bankController.saveResultOfCustomer(RequestService.resultCustomerInfoModels.get(i));

                }
                RequestService.resultCustomerInfoModels.clear();
                System.out.println("Bank capital was 40_000_000 AMD now it's -> " + RequestService.capitalOfBank + " AMD");
            }

        }

        return customerRequests.stream().map(CustomerResponse::getFromRequest).toList();
    }

    @PostMapping(value = "passList")
    public List<CustomerResponse> callGetInfo(@Valid @RequestBody final List<CustomerRequestFiltered> customerRequestFiltered) {
        List<CustomerResponse> customerResponses = null;
        for (final CustomerRequestFiltered crf: customerRequestFiltered) {
            customerResponses = getInfo(crf);
        }
        return customerResponses;
    }


    /**
     * Posts the accepted customer requests to the specified URL.
     * The customer request is saved as accepted customers by invoking the 'saveInfoAcceptedCustomers' method,
     * and then it is sent as a POST request to the specified URL.
     *
     * @param customerRequest The customer request object to be posted as accepted customers.
     * @return True if the POST request was successful and received a response indicating success, false otherwise.
     */

    private boolean postAcceptedRequests(final CustomerRequest customerRequest) {
        RequestService.capitalOfBank = RequestService.capitalOfBank - Long.parseLong
                (customerRequest.customerHistoryRequest().creditRequest().get(
                        customerRequest.customerHistoryRequest().creditRequest().size() - 1).loanAmount());
        bankController.saveOrUpdateAcceptedCustomers(customerRequest);
        final String urlRejected = "http://localhost:8080/Customer/saveCustomerOrUpdateCredit"; // url of postMethod where is going to be passed CustomerRequest
        RestTemplate postRequest = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomerRequest> customerRequestHttpEntity = new HttpEntity<>(customerRequest, httpHeaders);
        ResponseEntity<Boolean> response = postRequest.exchange(urlRejected, HttpMethod.POST, customerRequestHttpEntity, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    /**

     Posts the not accepted customer requests to the specified URL.
     The customer request is saved as accepted customers by invoking the 'saveInfoAcceptedCustomers' method,
     and then it is sent as a POST request to the specified URL.
     @param customerRequest The customer request object to be posted as not accepted customers.
     @return True if the POST request was successful and received a response indicating success, false otherwise.
     */
    private boolean postNotAcceptedRequests(final CustomerRequest customerRequest) {
        return bankController.saveOrUpdateRejectedCustomers(customerRequest) != null;
    }

    /**

     Creates a CustomerModel object based on the provided CustomerRequestFiltered.
     The method extracts the necessary data from the customerRequestFiltered parameter
     to create a new CustomerModel object.
     @param customerRequestFiltered The filtered customer request containing the necessary information.
     @return The created CustomerModel object.
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
                false,
                false
        ));
        return new CustomerModel(customerRequestFiltered, creditModels);
    }

}
