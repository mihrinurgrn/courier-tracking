package com.mng.couriertracking.service;

import com.mng.couriertracking.observer.StoreEntryObserver;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class StoreEntryLoggerService implements StoreEntryObserver {
    @Override
    public void onCourierEntry(String courierId, String storeName, LocalDateTime entryTime) {
        System.out.println("📜 Log: " + courierId + " kullanıcısı " + storeName + " mağazasına giriş yaptı. Zaman: " + entryTime);
    }
}

