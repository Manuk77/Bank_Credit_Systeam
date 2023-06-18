package com.example.acra.repository;

import com.example.acra.customer.entity.CustomerHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerHistoryRepository extends JpaRepository<CustomerHistoryEntity,Long> {
}

