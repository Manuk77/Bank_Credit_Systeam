package com.example.bank.customer.dto;


import com.example.bank.customer.entity.CustomerEntity;
import com.example.bank.customer.creating_requests.requests.CustomerInfoRequest;
import com.example.bank.customer.response.CustomerInfoResponse;

import java.util.Objects;

public class CustomerInfoModel {



    private  String firstName;
    private  String lastName;
    private  String birthDate;
    private  Integer age;
    private  String phone;
    private  String email;
    private Boolean flag;


    public CustomerInfoModel(final CustomerInfoRequest customerInfoRequest) {

        this.firstName = customerInfoRequest.firstName();
        this.lastName = customerInfoRequest.lastName();
        this.birthDate = customerInfoRequest.birthDate();
        this.age = Integer.parseInt(customerInfoRequest.age());
        this.phone = customerInfoRequest.phone();
        this.email = customerInfoRequest.email();
    }

    public CustomerInfoModel(final CustomerEntity customerEntity){
        this.firstName = customerEntity.getFirstName();
        this.lastName = customerEntity.getLastName();
        this.birthDate = customerEntity.getBirthDate().toString();
        this.age = customerEntity.getAge();
        this.email = customerEntity.getEmail();
        this.phone = customerEntity.getPhone();
        this.flag = customerEntity.getFlag();

    }

    public CustomerInfoModel(final CustomerInfoResponse customerInfoResponse) {
        this.firstName = customerInfoResponse.firstName();
        this.lastName = customerInfoResponse.lastName();
        this.birthDate = customerInfoResponse.birthDate();
        this.age = Integer.parseInt(customerInfoResponse.age());
        this.phone = customerInfoResponse.phone();
        this.email = customerInfoResponse.email();
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getFlag() {
        return flag;
    }


    @Override
    public String toString() {
        return "CustomerInfoModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", flag=" + flag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInfoModel that = (CustomerInfoModel) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate) && Objects.equals(age, that.age) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(flag, that.flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate, age, phone, email, flag);
    }
}
