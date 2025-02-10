package com.mng.couriertracking.config;

import com.mng.couriertracking.service.StoreEntryLoggerService;
import com.mng.couriertracking.service.StoreEntrySaveService;
import com.mng.couriertracking.subject.StoreEntrySubject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverConfig {
    @Bean
    public StoreEntrySubject storeEntrySubject(StoreEntrySaveService storeEntrySaveService, StoreEntryLoggerService loggerService) {
        StoreEntrySubject subject = new StoreEntrySubject();
        subject.addObserver(storeEntrySaveService);
        subject.addObserver(loggerService);
        return subject;
    }
}
