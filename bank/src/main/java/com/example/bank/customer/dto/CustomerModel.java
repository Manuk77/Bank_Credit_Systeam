package com.example.bank.customer.dto;




import com.example.bank.customer.entity.CustomerEntity;



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
}
