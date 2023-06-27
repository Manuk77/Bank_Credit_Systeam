package com.example.bank.repository;

import com.example.bank.customer.entity.CreditEntity;
import com.example.bank.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
}
