package com.example.bank.repository;

import com.example.bank.customer.entity.ResultCustomerInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultCustomerInfoRepository extends JpaRepository<ResultCustomerInfoEntity, Long> {
}
