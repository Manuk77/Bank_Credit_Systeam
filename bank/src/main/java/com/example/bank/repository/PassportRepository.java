package com.example.bank.repository;

import com.example.bank.customer.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
}
