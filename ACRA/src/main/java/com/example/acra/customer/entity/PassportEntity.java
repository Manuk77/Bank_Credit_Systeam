package com.example.acra.customer.entity;

import com.example.acra.customer.dto.PassportModel;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "passport")
public class PassportEntity {

    @Id
    @Column(name = "passport_number", nullable = false, length = 15, unique = true)
    private String passportNumber;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(name = "date_birthday", nullable = true)
   // @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "issue", nullable = false)
    private Date issue;
    @Column(name = "expiry", nullable = false)
    private Date expiry;
    @Column(name = "authority", nullable = false, length = 50)
    private String authority;
    @Column(name = "gender", nullable = false, length = 6)
    private String gender;
    @Column(name = "flag", nullable = false)
    private Boolean flag;
    @OneToOne(mappedBy = "passport")
    private CustomerEntity customerEntity;

    public PassportEntity() {
    }

    public PassportEntity(final CustomerEntity customerEntity,
                          final String passportNumber,
                          final Date issue,
                          final Date expiry,
                          final String authority,
                          final String gender,
                          final Boolean flag) {
        this.customerEntity = customerEntity;
        this.passportNumber = passportNumber;
        this.issue = issue;
        this.expiry = expiry;
        this.authority = authority;
        this.gender = gender;
        this.flag = flag;
    }
   public PassportEntity(PassportModel passportModel){
        this.passportNumber = passportModel.getPassportNumber();
        this.expiry = passportModel.getExpiryDate();
        this.issue = passportModel.getIssueDate();
        this.flag = true;
        this.authority = passportModel.getAuthority();
        this.gender = passportModel.getGender();
        this.firstName = passportModel.getFirstName();
        this.lastName = passportModel.getLastName();
        this.birthDate = passportModel.getBirthDate();
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

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getIssue() {
        return issue;
    }

    public void setIssue(Date issue) {
        this.issue = issue;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}