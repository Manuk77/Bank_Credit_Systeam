package com.example.bank.controller;



import com.example.bank.customer.dto.*;
import com.example.bank.customer.response.CustomerResponse;
import com.example.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@RestController
@RequestMapping(value = "/bank")
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(final BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping(value = "/getInfo/{passportNumber}")
    public @ResponseBody CustomerResponse newCredit(@PathVariable @NonNull final String passportNumber) {

        final String path = "http://localhost:8080/Customer/getInfo/" + passportNumber;
        RestTemplate restTemplate = new RestTemplate();
        Optional<CustomerResponse> customerR = Optional.ofNullable(
                restTemplate.getForObject(path, CustomerResponse.class));

        if (customerR.filter(response -> bankService.saveCustomer(new AddressModel(response.addressResponse()),
                new PassportModel(response.passportResponse()),
                new CustomerInfoModel(response.customerInfoResponse()),
                new CustomerHistoryModel(response.customerHistoryResponse()),
                new WorkingPlaceModel(response.workingPlaceResponse()))).isPresent()) {
            return customerR.get();
        }
        return null;

    }
}
