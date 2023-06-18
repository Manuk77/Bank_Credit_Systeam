package com.example.acra.repository;

import com.example.acra.customer.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<PassportEntity,String> {
}
