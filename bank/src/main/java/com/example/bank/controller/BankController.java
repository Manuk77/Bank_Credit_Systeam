package com.example.bank.controller;



import com.example.bank.customer.creating_requests.requests.CreditRequest;
import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import com.example.bank.customer.dto.*;
import com.example.bank.customer.response.*;
import com.example.bank.service.BankService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

/**
 * Represents a REST ful API for banking operations.
 * Base URL: /bank
 */
@RestController
@RequestMapping(value = "/bank")
public class BankController {
    private final BankService bankService;

    /**
     * Constructs a new instance of BankController with the provided BankService.
     *
     * @param bankService The BankService used for banking operations.
     */
    @Autowired
    public BankController(final BankService bankService) {
        this.bankService = bankService;
    }



    /**
     * Retrieves customer information based on the provided passport number.
     * Performs a GET request to the external service and saves the retrieved customer information
     * to the local database if it is present.
     *
     * @param passportNumber The passport number of the customer.
     * @return The response containing the customer information if it was successfully saved, or null otherwise.
     */
    @PostMapping(value = "/getInfo/{passportNumber}")
    public @ResponseBody CustomerResponse newCredit(@NonNull @PathVariable final String passportNumber) {

        final String path = "http://localhost:8080/Customer/getInfo/" + passportNumber;
        RestTemplate restTemplate = new RestTemplate();
        Optional<CustomerResponse> customerR = Optional.ofNullable(
                restTemplate.getForObject(path, CustomerResponse.class));

        if (customerR.filter(response -> bankService.saveCustomer(
                new AddressModel(response.addressResponse()),
                new PassportModel(response.passportResponse()),
                new CustomerInfoModel(response.customerInfoResponse()),
                new CustomerHistoryModel(response.customerHistoryResponse()),
                new WorkingPlaceModel(response.workingPlaceResponse()))).isPresent()) {
            return customerR.get();
        }
        return null;

    }

    /**
     * Retrieves the customer information based on the provided passport number.
     *
     * @param passportNumber The passport number of the customer.
     * @return The response containing the customer information, or null if the customer information is not found.
     */
    @GetMapping(value = "/getInfo/{passportNumber}")
    public  @ResponseBody CustomerResponse getCustomerInfo(@NonNull @PathVariable final String passportNumber) {
         return CustomerResponse.getFromModel(bankService.getInfo(passportNumber));
    }

    /**
     * Saves the customer information for rejected applications.
     * Performs a POST request to persist the customer information in the local database.
     *
     * @param customerRequest The request object containing the customer information to be saved.
     * @return The response containing the saved customer information if the save operation was successful, or null otherwise.
     */
    @PostMapping(value = "save/rejected/customers")
    public @ResponseBody CustomerResponse saveInfoRejectedCustomers(@RequestBody @Valid final CustomerRequest customerRequest){
        // RestTemplate if it rejected
       if (bankService.saveCustomer(new AddressModel(customerRequest.addressRequest()),
                new PassportModel(customerRequest.passportRequest()),
                new CustomerInfoModel(customerRequest.customerInfoRequest()),
                new CustomerHistoryModel(customerRequest.customerHistoryRequest()),
                new WorkingPlaceModel(customerRequest.workingPlaceRequest())))
           return CustomerResponse.getFromRequest(customerRequest);
       return null;
    }

    /**
     * Adds a new credit for a customer with the specified passport number.
     *
     * @param creditRequest   The request object containing the details of the new credit to be added.
     * @param passportNumber  The passport number of the customer.
     * @return True if the new credit was successfully added, false otherwise.
     */
    @PatchMapping(value = "addNewCredit/{passportNumber}")
    public boolean addNewCredit(@NonNull @RequestBody CreditRequest creditRequest,
                                @NonNull @PathVariable final String passportNumber) {

        return bankService.addNewCredit(new CreditModel(creditRequest), passportNumber);
    }

    /**
     * Saves the customer information for accepted applications.
     * Performs a POST request to persist the customer information in the local database.
     *
     * @param customerRequest The request object containing the customer information to be saved.
     * @return The response containing the saved customer information if the save operation was successful, or null otherwise.
     */
    @PostMapping(value = "save/accepted/customers")
    public @ResponseBody CustomerResponse saveInfoAcceptedCustomers(@Valid @RequestBody final CustomerRequest customerRequest) {
        // RestTemplate getFrom ACRA

        if (bankService.saveCustomer(new AddressModel(customerRequest.addressRequest()),
                new PassportModel(customerRequest.passportRequest()),
                new CustomerInfoModel(customerRequest.customerInfoRequest()),
                new CustomerHistoryModel(customerRequest.customerHistoryRequest()),
                new WorkingPlaceModel(customerRequest.workingPlaceRequest())))

            return CustomerResponse.getFromRequest(customerRequest);

        return null;
    }


}
