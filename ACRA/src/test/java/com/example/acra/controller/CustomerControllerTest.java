package com.example.acra.controller;

import com.example.acra.customer.dto.*;
import com.example.acra.customer.response.*;
import com.example.acra.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInfo() {


        AddressResponse addressResponse = new AddressResponse("Abovyan", "Yerevan", "Armenia");
        PassportResponse passportResponse = new PassportResponse("Arin", "Avaneusyan", "2001-12-31", "male",
                "AA8800107", "2019-04-21", "2029-04-21", "ARM 1");
        CustomerInfoResponse customerInfoResponse = new CustomerInfoResponse("Arin", "Avaneusyan",
                "2001-12-31", "21", "+374 56-98-98-98", "arfdf@gmail.com");
        WorkingPlaceResponse workingPlaceResponse = new WorkingPlaceResponse("AUA", "80000");
        CustomerHistoryResponse customerHistoryResponse = new CustomerHistoryResponse("267900", "true",
                "591", List.of(
                new CreditResponse("AMERIA", "362150", "CREDIT", "39836.50000000001", "2023-06-28", "2025-06-28",
                        "true", "true", "true","10")
        ));

        CustomerResponse mockResponse = new CustomerResponse(addressResponse, passportResponse, customerInfoResponse,
                workingPlaceResponse, customerHistoryResponse);

        CustomerModel customerModel = CustomerModel
                .builder()
                .addressModel(new AddressModel(mockResponse.addressResponse()))
                .passportModel(new PassportModel(mockResponse.passportResponse()))
                .customerInfoModel(new CustomerInfoModel(mockResponse.customerInfoResponse()))
                .workingPlaceModel(new WorkingPlaceModel(mockResponse.workingPlaceResponse()))
                .customerHistoryModel(new CustomerHistoryModel(mockResponse.customerHistoryResponse()))
                .build();

        when(customerService.getInfo("AA8800107")).thenReturn(customerModel);

        CustomerResponse response = customerController.getInfo("AA8800107");


        assertEquals(addressResponse, response.addressResponse());
        assertEquals(passportResponse, response.passportResponse());
        assertEquals(customerInfoResponse, response.customerInfoResponse());
        assertEquals(workingPlaceResponse, response.workingPlaceResponse());
        assertEquals(customerHistoryResponse, response.customerHistoryResponse());
    }
}