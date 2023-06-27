package com.example.bank.repository;

import com.example.bank.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findCustomerEntityByPassport_PassportNumber(final String passportNumber);
}
