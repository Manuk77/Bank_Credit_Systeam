package com.example.acra.customer.dto;

import com.example.acra.customer.entity.CustomerEntity;
import com.example.acra.customer.requests.creating_requests.CustomerInfoRequest;
import com.example.acra.customer.response.CustomerInfoResponse;
import lombok.Builder;

@Builder
public class CustomerInfoModel {


    
    private  String firstName;
    private  String lastName;
    private  String birthDate;
    private  Integer age;
    private  String phone;
    private  String email;
    private Boolean flag;

    public CustomerInfoModel(String firstName,
                             String lastName,
                             String birthDate,
                             Integer age,
                             String phone,
                             String email,
                             Boolean flag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.flag = flag;
    }

    public CustomerInfoModel(final CustomerInfoRequest customerInfoRequest) {
        
        this.firstName = customerInfoRequest.firstName();
        this.lastName = customerInfoRequest.lastName();
        this.birthDate = customerInfoRequest.birthDate();
        this.age = Integer.parseInt(customerInfoRequest.age());
        this.phone = customerInfoRequest.phone();
        this.email = customerInfoRequest.email();
    }
    public CustomerInfoModel(final CustomerInfoResponse customerInfoRequest) {

        this.firstName = customerInfoRequest.firstName();
        this.lastName = customerInfoRequest.lastName();
        this.birthDate = customerInfoRequest.birthDate();
        this.age = Integer.parseInt(customerInfoRequest.age());
        this.phone = customerInfoRequest.phone();
        this.email = customerInfoRequest.email();
    }
  
    public CustomerInfoModel(CustomerEntity customerEntity){
        this.firstName = customerEntity.getFirstName();
        this.lastName = customerEntity.getLastName();
        this.birthDate = customerEntity.getBirthDate().toString();
        this.age = customerEntity.getAge();
        this.email = customerEntity.getEmail();
        this.phone = customerEntity.getPhone();
        this.flag = customerEntity.getFlag();

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

  
    public Boolean getFlag() {
        return flag;
    }


}
