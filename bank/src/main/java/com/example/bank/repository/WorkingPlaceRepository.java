package com.example.bank.repository;

import com.example.bank.customer.entity.WorkingPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingPlaceRepository extends JpaRepository<WorkingPlaceEntity, Long> {
}
