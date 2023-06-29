package com.example.bank.customer.dto;
import com.example.bank.customer.creating_requests.requests.CustomerRequestFiltered;
import com.example.bank.customer.entity.CustomerEntity;
import com.example.bank.customer.response.CustomerResponse;

import java.util.List;
import java.util.Objects;


public class CustomerModel {

    private AddressModel addressModel;
    private PassportModel passportModel;
    private CustomerInfoModel customerInfoModel;
    private WorkingPlaceModel workingPlaceModel;
    private CustomerHistoryModel customerHistoryModel;



    public CustomerModel(final CustomerEntity customerEntity) {
        this.passportModel = new PassportModel(customerEntity.getPassport());
        this.addressModel = new AddressModel(customerEntity.getAddress());
        this.customerInfoModel = new CustomerInfoModel(customerEntity);
        this.workingPlaceModel = new WorkingPlaceModel(customerEntity.getWorkingPlace());
        this.customerHistoryModel = new CustomerHistoryModel(customerEntity.getCustomerHistory());
    }

    public CustomerModel(final CustomerResponse customerResponse) {
        this.addressModel = new AddressModel(customerResponse.addressResponse());
        this.passportModel = new PassportModel(customerResponse.passportResponse());
        this.customerInfoModel = new CustomerInfoModel(customerResponse.customerInfoResponse());
        this.customerHistoryModel = new CustomerHistoryModel(customerResponse.customerHistoryResponse());
        this.workingPlaceModel = new WorkingPlaceModel(customerResponse.workingPlaceResponse());
    }

    public CustomerModel(final CustomerRequestFiltered customerRequestFiltered, final List<CreditModel> creditModel) {
        this.addressModel = new AddressModel(customerRequestFiltered.addressRequest());
        this.passportModel = new PassportModel(customerRequestFiltered.passportRequest());
        this.customerInfoModel = new CustomerInfoModel(customerRequestFiltered.customerInfoRequest());
        this.customerHistoryModel = new CustomerHistoryModel(
                customerRequestFiltered.workingPlaceRequest().salary(),
                false,
                (short)600,
                creditModel);
        this.workingPlaceModel = new WorkingPlaceModel(customerRequestFiltered.workingPlaceRequest());
    }


    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public PassportModel getPassportModel() {
        return passportModel;
    }

    public void setPassportModel(PassportModel passportModel) {
        this.passportModel = passportModel;
    }

    public CustomerInfoModel getCustomerInfoModel() {
        return customerInfoModel;
    }

    public void setCustomerInfoModel(CustomerInfoModel customerInfoModel) {
        this.customerInfoModel = customerInfoModel;
    }

    public WorkingPlaceModel getWorkingPlaceModel() {
        return workingPlaceModel;
    }

    public void setWorkingPlaceModel(WorkingPlaceModel workingPlaceModel) {
        this.workingPlaceModel = workingPlaceModel;
    }

    public CustomerHistoryModel getCustomerHistoryModel() {
        return customerHistoryModel;
    }

    public void setCustomerHistoryModel(CustomerHistoryModel customerHistoryModel) {
        this.customerHistoryModel = customerHistoryModel;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "addressModel=" + addressModel + "\n" +
                ", passportModel=" + passportModel + "\n" +
                ", customerInfoModel=" + customerInfoModel + "\n" +
                ", workingPlaceModel=" + workingPlaceModel + "\n" +
                ", customerHistoryModel=" + customerHistoryModel + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel that = (CustomerModel) o;
        return Objects.equals(passportModel, that.passportModel) &&
                Objects.equals(customerHistoryModel, that.customerHistoryModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passportModel, customerHistoryModel);
    }
}
