package com.example.bank.controller;


import com.example.bank.customer.creating_requests.requests.CustomerRequest;
import com.example.bank.customer.dto.*;
import com.example.bank.customer.response.*;
import com.example.bank.service.BankService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping(value = "saveOrUpdateRejectedCustomers")
    public @ResponseBody CustomerResponse saveOrUpdateRejectedCustomers(@Valid @RequestBody final CustomerRequest customerRequest){
        // RestTemplate if it rejected
           if (bankService.saveNotAcceptedCustomerCredit(
                   new AddressModel(customerRequest.addressRequest()),
                   new PassportModel(customerRequest.passportRequest()),
                   new CustomerInfoModel(customerRequest.customerInfoRequest()),
                   new CustomerHistoryModel(customerRequest.customerHistoryRequest()),
                   new WorkingPlaceModel(customerRequest.workingPlaceRequest())))
               return CustomerResponse.getFromRequest(customerRequest);
       return null;
    }

    /**
     * Saves the customer information for accepted applications.
     * Performs a POST request to persist the customer information in the local database.
     *
     * @param customerRequest The request object containing the customer information to be saved.
     * @return The response containing the saved customer information if the save operation was successful, or null otherwise.
     */
    @PostMapping(value = "saveOrUpdateAcceptedCustomers")
    public @ResponseBody CustomerResponse saveOrUpdateAcceptedCustomers(@Valid @RequestBody final CustomerRequest customerRequest) {
        // RestTemplate getFrom ACRA

        if (bankService.saveAcceptedCustomerCredit(new AddressModel(customerRequest.addressRequest()),
                new PassportModel(customerRequest.passportRequest()),
                new CustomerInfoModel(customerRequest.customerInfoRequest()),
                new CustomerHistoryModel(customerRequest.customerHistoryRequest()),
                new WorkingPlaceModel(customerRequest.workingPlaceRequest())))

            return CustomerResponse.getFromRequest(customerRequest);

        return null;
    }
    @PostMapping(value = "saveResultCustomerInfo")
    public void saveResultOfCustomer(@NonNull final ResultCustomerInfoModel resultCustomerInfoModel) {
        bankService.saveResultOfCustomer(resultCustomerInfoModel);
    }



}
