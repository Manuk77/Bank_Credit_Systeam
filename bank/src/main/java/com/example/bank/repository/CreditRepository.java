package com.example.bank.repository;

import com.example.bank.customer.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
}
