package com.example.acra.customer.dto;
import com.example.acra.customer.entity.PassportEntity;
import com.example.acra.customer.requests.creating_requests.PassportRequest;
import com.example.acra.customer.response.PassportResponse;

import java.sql.Date;



public class PassportModel {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String passport_number;
    private Date issueDate;
    private Date expiryDate;
    private String authority;



    public PassportModel(final PassportEntity passportEntity) {
        this.authority = passportEntity.getAuthority();
        this.gender = passportEntity.getGender();
        this.issueDate = passportEntity.getIssue();
        this.expiryDate = passportEntity.getExpiry();
        this.passport_number = passportEntity.getPassportNumber();
        this.birthDate =passportEntity.getBirthDate();
        this.firstName = passportEntity.getFirstName();
        this.lastName = passportEntity.getLastName();
    }
    public PassportModel(final PassportRequest passportRequest) {
        this.authority = passportRequest.authority();
        this.gender = passportRequest.gender();
        this.issueDate = Date.valueOf(passportRequest.issueDate());
        this.expiryDate = Date.valueOf(passportRequest.expiryDate());
        this.passport_number = passportRequest.passport_number();
        this.firstName = passportRequest.firstName();
        this.lastName = passportRequest.lastName();
        this.birthDate = Date.valueOf(passportRequest.birthDate());
    }
    public PassportModel(final PassportResponse passportRequest) {
        this.authority = passportRequest.authority();
        this.gender = passportRequest.gender();
        this.issueDate = Date.valueOf(passportRequest.issueDate());
        this.expiryDate = Date.valueOf(passportRequest.expiryDate());
        this.passport_number = passportRequest.passportNumber();
        this.firstName = passportRequest.firstName();
        this.lastName = passportRequest.lastName();
        this.birthDate = Date.valueOf(passportRequest.birthDate());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassportNumber() {
        return passport_number;
    }

    public void setSerialNumber(String serialNumber) {
        this.passport_number = serialNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
