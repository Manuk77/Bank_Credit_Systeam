package com.example.acra.customer.dto;

import com.example.acra.customer.entity.AddressEntity;
import com.example.acra.customer.requests.creating_requests.AddressRequest;
import com.example.acra.customer.response.AddressResponse;

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
    public AddressModel(final AddressResponse address) {
       this.city = address.city();
       this.country = address.country();
       this.street = address.street();
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
}
