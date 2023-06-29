package com.example.bank.customer.entity;



import com.example.bank.bank_entity.BankEntity;
import com.example.bank.customer.dto.CustomerInfoModel;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(name = "date_birthday", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "age",nullable = false)
    private Integer age;
    @Column(name = "phone", nullable = false, length = 32)
    private String phone;
    @Column(name = "email", nullable = false, length = 32)
    private String email;
    @Column(name = "flag", nullable = false)
    private Boolean flag;
    @OneToOne()
    @JoinColumn(name = "passport",referencedColumnName = "passport_number",nullable = false)
    private PassportEntity passport;
    @OneToOne()
    @JoinColumn(name = "history",referencedColumnName = "history_id")
    private CustomerHistoryEntity customerHistory;
    @ManyToOne
    @JoinColumn(name = "address")
    private AddressEntity address;
    @ManyToOne
    @JoinColumn(name = "working_place_id")
    private WorkingPlaceEntity workingPlace;
    @ManyToOne()
    @JoinColumn(name = "bank_id")
    private BankEntity bank;

    public CustomerEntity() {
    }

    public CustomerEntity(
            final PassportEntity passportEntity,
            final CustomerHistoryEntity historyList,
            final AddressEntity address,
            final WorkingPlaceEntity workingPlace,
            final CustomerInfoModel customerInfoModel) {

        this.passport = passportEntity;
        this.flag = true;
        this.customerHistory = historyList;
        this.address = address;
        this.workingPlace = workingPlace;
        setFirstName(customerInfoModel.getFirstName());
        setLastName(customerInfoModel.getLastName());
        setAge(customerInfoModel.getAge());
        setBirthDate(Date.valueOf(customerInfoModel.getBirthDate()));
        setEmail(customerInfoModel.getEmail());
        setPhone(customerInfoModel.getPhone());
    }




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(final Boolean flag) {
        this.flag = flag;
    }

    public WorkingPlaceEntity getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(final WorkingPlaceEntity workingPlace) {
        this.workingPlace = workingPlace;
    }

    public PassportEntity getPassport() {
        return passport;
    }

    public void setPassport(final PassportEntity passport) {
        this.passport = passport;
    }

    public CustomerHistoryEntity getCustomerHistory() {
        return customerHistory;
    }

    public void setCustomerHistory(final CustomerHistoryEntity customerHistory) {
        this.customerHistory = customerHistory;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(final AddressEntity address) {
        this.address = address;
    }


}
