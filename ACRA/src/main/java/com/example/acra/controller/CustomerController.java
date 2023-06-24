package com.example.acra.controller;

import com.example.acra.annotation.NotNullEmptyBlankString;
import com.example.acra.customer.dto.*;
import com.example.acra.customer.requests.creating_requests.CreditRequest;
import com.example.acra.customer.requests.creating_requests.CustomerRequest;
import com.example.acra.customer.response.CustomerResponse;
import com.example.acra.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping(value = "/saveCustomer")
    public Boolean saveInfo(@Valid @RequestBody final CustomerRequest customerRequest) {
        return customerService.saveCustomer(
                new AddressModel(customerRequest.addressRequest()),
                new PassportModel(customerRequest.passportRequest()),
                new CustomerInfoModel(customerRequest.customerInfoRequest()),
                new CustomerHistoryModel(customerRequest.customerHistoryRequest()),
                new WorkingPlaceModel(customerRequest.workingPlaceRequest()));
    }


    @PatchMapping(value = "/updateCredit/{passportNumber}")
    public Boolean updateCredit(@RequestBody  final CreditRequest creditRequest,
                                @PathVariable @NonNull final  String passportNumber) {

        return customerService.updateCredit(new CreditModel(creditRequest), passportNumber);
    }

    @GetMapping(value = "/getInfo/{passportNumber}")
    public @ResponseBody CustomerResponse getInfo(@PathVariable @NonNull final String passportNumber) {
        return CustomerResponse.getFromModel(customerService.getInfo(passportNumber));
    }

    @GetMapping(value = "/getInfo/{firstName}/{lastName}")
    public @ResponseBody CustomerResponse getInfo(@PathVariable @NonNull final String firstName,
                                                  @PathVariable @NonNull final String lastName) {
        return CustomerResponse.getFromModel(customerService.getInfo(firstName, lastName));
    }


}
