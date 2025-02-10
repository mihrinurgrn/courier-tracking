package com.mng.couriertracking.observer;

import java.time.LocalDateTime;

public interface StoreEntryObserver {
    void onCourierEntry(String courierId, String storeName, LocalDateTime entryTime);
}

