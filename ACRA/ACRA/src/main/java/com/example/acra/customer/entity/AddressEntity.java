package com.example.acra.customer.entity;

import com.example.acra.customer.dto.AddressModel;
import jakarta.persistence.*;

import java.util.List;

@Entity()
@Table(name = "addresses",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"country", "city", "street"})
        }
)
public class AddressEntity {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "country", nullable = false, length = 25)
    private String country;
    @Column(name = "city", nullable = false, length = 25)
    private String city;
    @Column(name = "street",nullable = false,length = 25)
    private String street;

    @OneToMany(mappedBy = "address")
    private List<CustomerEntity> customers;


    public AddressEntity () {
    }

    public AddressEntity (final String country, final String city,final String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public AddressEntity(final AddressEntity AddressEntity) {
        this.country = AddressEntity.country;
        this.city = AddressEntity.city;
        this.street = AddressEntity.street;
    }

    public AddressEntity(final AddressModel addressModel) {
        this.city = addressModel.getCity();
        this.country = addressModel.getCountry();
        this.street = addressModel.getStreet();
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
