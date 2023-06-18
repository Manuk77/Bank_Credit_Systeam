package com.example.bank.repository;

import com.example.bank.customer.entity.CustomerHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerHistoryRepository extends JpaRepository<CustomerHistoryEntity, Long> {
}
