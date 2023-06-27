package com.example.acra.controller;


import com.example.acra.customer.dto.*;
import com.example.acra.customer.requests.creating_requests.CustomerRequest;
import com.example.acra.customer.response.CustomerResponse;
import com.example.acra.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * The `CustomerController` class is a REST controller that handles customer-related HTTP requests.
 * It exposes endpoints for managing customer information, such as saving customer details, adding new credits,
 * and retrieving customer information.
 */
@RestController
@RequestMapping(value = "/Customer")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructs a new instance of the `CustomerController` class.
     *
     * @param customerService The customer service used for handling customer-related operations.
     */
    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Saves customer information by creating a new customer record.
     *
     * @param customerRequest The customer request object containing the customer details to be saved.
     * @return `true` if the customer information is successfully saved, `false` otherwise.
     */
    @PostMapping(value = "/saveCustomerOrUpdateCredit")
    public Boolean saveCustomerOrUpdateCredit(@Valid @RequestBody final CustomerRequest customerRequest) {
        return customerService.saveNewCustomerOrUpdateCredit(
                new AddressModel(customerRequest.addressRequest()),
                new PassportModel(customerRequest.passportRequest()),
                new CustomerInfoModel(customerRequest.customerInfoRequest()),
                new CustomerHistoryModel(customerRequest.customerHistoryRequest()),
                new WorkingPlaceModel(customerRequest.workingPlaceRequest()));
    }


    /**
     * Retrieves the customer information for the customer with the specified passport number.
     *
     * @param passportNumber The passport number of the customer.
     * @return The customer response object containing the retrieved customer information.
     */
    @GetMapping(value = "/getInfo/{passportNumber}")
    public @ResponseBody CustomerResponse getInfo(@NonNull @PathVariable final String passportNumber) {
        return CustomerResponse.getFromModel(customerService.getInfo(passportNumber));
    }

    /**
     * Retrieves the customer information for the customer with the specified first name and last name.
     *
     * @param firstName The first name of the customer.
     * @param lastName  The last name of the customer.
     * @return The customer response object containing the retrieved customer information.
     */
    @GetMapping(value = "/getInfo/{firstName}/{lastName}")
    public @ResponseBody CustomerResponse getInfo(@NonNull @PathVariable final String firstName,
                                                  @NonNull @PathVariable final String lastName) {
        return CustomerResponse.getFromModel(customerService.getInfo(firstName, lastName));
    }


}
