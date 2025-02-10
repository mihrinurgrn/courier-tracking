package com.mng.couriertracking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestConfiguration
public class StoreEntryLoggerServiceTest {

    @InjectMocks
    private StoreEntryLoggerService storeEntryLoggerService;

    // ByteArrayOutputStream to capture System.out.println output
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock nesnelerinin başlatılması
        System.setOut(new PrintStream(outputStreamCaptor)); // System.out.println çıktısını yakalamak için
    }

    @Test
    void onCourierEntryShouldLogCorrectMessage_WhenValidDataProvided() {
        // Arrange
        String courierId = "123";
        String storeName = "Test Store";
        LocalDateTime entryTime = LocalDateTime.now();

        // Act
        storeEntryLoggerService.onCourierEntry(courierId, storeName, entryTime);

        // Assert
        String expectedLogMessage = "📜 Log: 123 kullanıcısı Test Store mağazasına giriş yaptı. Zaman: " + entryTime;
        assertTrue(outputStreamCaptor.toString().contains(expectedLogMessage),
                "The log message should contain the expected text.");
    }
}

