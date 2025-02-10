package com.mng.couriertracking.repository;

import com.mng.couriertracking.model.StoreEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreEntryRepository extends JpaRepository<StoreEntry, Long> {
    Optional<StoreEntry> findTopByCourierIdAndStoreNameOrderByEntryTimeDesc(String courierId, String storeName);
}
