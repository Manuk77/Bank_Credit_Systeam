package com.example.bank.bank_model;


import com.example.bank.bank_entity.Role;


public class EmployeeModel {

    private String firstName;
    private String lastName;
    private Byte age;



    public EmployeeModel() {
    }

    public EmployeeModel(final String firstName, final String lastName,
                         final Byte age) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;

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

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }


}
