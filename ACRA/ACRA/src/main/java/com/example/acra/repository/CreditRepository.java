package com.example.acra.repository;

import com.example.acra.customer.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditRepository extends JpaRepository<CreditEntity,Long> {

}
