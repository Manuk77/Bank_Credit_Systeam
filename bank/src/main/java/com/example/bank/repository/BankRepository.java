package com.example.bank.repository;


import com.example.bank.bank_entity.BankEntity;
import com.example.bank.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<BankEntity, Long> {
}
