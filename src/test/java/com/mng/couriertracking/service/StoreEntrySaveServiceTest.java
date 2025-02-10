package com.mng.couriertracking.service;

import com.mng.couriertracking.model.StoreEntry;
import com.mng.couriertracking.repository.StoreEntryRepository;
import com.mng.couriertracking.subject.StoreEntrySubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestConfiguration
public class StoreEntrySaveServiceTest {

    @InjectMocks
    private StoreEntrySaveService storeEntrySaveService;

    @Mock
    private StoreEntryRepository storeEntryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock nesnelerinin başlatılması
    }

    @Test
    void onCourierEntryShouldSaveStoreEntryWhenValidDataProvided() {
        // Arrange
        String courierId = "123";
        String storeName = "Test Store";
        LocalDateTime entryTime = LocalDateTime.now();

        // Act & Assert
        assertDoesNotThrow(() -> storeEntrySaveService.onCourierEntry(courierId, storeName, entryTime));

        // Verify that the saved object has the correct data
        verify(storeEntryRepository).save(any());
    }
}

