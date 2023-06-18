package com.example.acra.repository;

import com.example.acra.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findCustomerEntityByPassport_PassportNumber(final String passportNumber);
    Optional<CustomerEntity> findCustomerEntityByPassport_FirstNameAndLastName(final String firstName, final String lastName);
}
