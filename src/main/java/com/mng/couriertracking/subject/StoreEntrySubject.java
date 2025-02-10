package com.mng.couriertracking.subject;

import com.mng.couriertracking.observer.StoreEntryObserver;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class StoreEntrySubject {
    private final List<StoreEntryObserver> observers = new ArrayList<>();

    public void addObserver(StoreEntryObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String courierId, String storeName, LocalDateTime entryTime) {
        for (StoreEntryObserver observer : observers) {
            observer.onCourierEntry(courierId, storeName, entryTime);
        }
    }
}

