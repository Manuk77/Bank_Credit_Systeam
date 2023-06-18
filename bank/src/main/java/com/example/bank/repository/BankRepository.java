package com.example.bank.repository;


import com.example.bank.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<CustomerEntity, Long> {

}
