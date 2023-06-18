package com.example.acra.repository;

import com.example.acra.customer.entity.WorkingPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingPlaceRepository extends JpaRepository<WorkingPlaceEntity, Long> {
}
