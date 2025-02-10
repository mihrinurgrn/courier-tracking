package com.mng.couriertracking.service;

import com.mng.couriertracking.model.StoreEntry;
import com.mng.couriertracking.observer.StoreEntryObserver;
import com.mng.couriertracking.repository.StoreEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StoreEntrySaveService implements StoreEntryObserver {

    @Autowired
    StoreEntryRepository storeEntryRepository;

    @Override
    public void onCourierEntry(String courierId, String storeName, LocalDateTime entryTime) {
        StoreEntry storeEntry = new StoreEntry();
        storeEntry.setCourierId(courierId);
        storeEntry.setEntryTime(entryTime);
        storeEntry.setStoreName(storeName);
        storeEntryRepository.save(storeEntry);
        System.out.println("💾 Kaydedildi: " + courierId + " - " + storeName + " - " + entryTime);
    }
}
