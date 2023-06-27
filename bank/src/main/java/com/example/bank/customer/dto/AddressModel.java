package com.example.bank.customer.dto;

import com.example.bank.customer.creating_requests.requests.AddressRequest;
import com.example.bank.customer.entity.AddressEntity;
import com.example.bank.customer.response.AddressResponse;

import java.util.Objects;


public class AddressModel {
    private String country;
    private String city;
    private String street;

    public AddressModel(final AddressEntity address) {
        this.city = address.getCity();
        this.country = address.getCountry();
        this.street = address.getStreet();
    }

    public AddressModel(final AddressRequest address) {
        this.city = address.city();
        this.country = address.country();
        this.street = address.street();
    }

    public AddressModel(final AddressResponse addressResponse) {
        this.city = addressResponse.city();
        this.country = addressResponse.country();
        this.street = addressResponse.street();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressModel that = (AddressModel) o;
        return Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street);
    }
}
