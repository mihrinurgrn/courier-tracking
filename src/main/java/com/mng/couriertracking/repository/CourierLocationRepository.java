package com.mng.couriertracking.repository;

import com.mng.couriertracking.model.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierLocationRepository extends JpaRepository<CourierLocation,Long> {
    List<CourierLocation> findByCourierId(String courierId);
}
