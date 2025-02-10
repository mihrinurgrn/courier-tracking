package com.mng.couriertracking.service;

import com.mng.couriertracking.dto.CourierLocationRequest;
import com.mng.couriertracking.model.CourierLocation;
import com.mng.couriertracking.model.Store;
import com.mng.couriertracking.repository.CourierLocationRepository;
import com.mng.couriertracking.repository.StoreEntryRepository;
import com.mng.couriertracking.repository.StoreRepository;
import com.mng.couriertracking.subject.StoreEntrySubject;
import com.mng.couriertracking.utils.LocationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestConfiguration
public class CourierLocationServiceTest {

    @InjectMocks
    private CourierLocationService courierLocationService;

    @Mock
    private CourierLocationRepository courierLocationRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreEntryRepository storeEntryRepository;

    @Mock
    private StoreEntrySubject storeEntrySubject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void saveCourierLocationShouldSaveLocationAndNotifyObserverWhenStoreIsNearby() {
        // Arrange
        CourierLocationRequest request = new CourierLocationRequest();
        request.setCourierId("123");
        request.setLatitude(41.0);
        request.setLongitude(29.0);
        request.setTime(LocalDateTime.now());

        Store mockStore = new Store();
        mockStore.setName("Test Store");
        mockStore.setLatitude(41.0005);
        mockStore.setLongitude(29.0005);

        when(storeRepository.findAll()).thenReturn(List.of(mockStore));
        when(storeEntryRepository.findTopByCourierIdAndStoreNameOrderByEntryTimeDesc(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Act
        courierLocationService.saveCourierLocation(request);

        // Assert
        verify(courierLocationRepository, times(1)).save(any());
        verify(storeEntrySubject).notifyObservers(
                eq("123"), eq("Test Store"), any(LocalDateTime.class));

    }

    @Test
    void saveCourierLocationShouldNotNotifyObserverWhenNoNearbyStore() {
        // Arrange
        CourierLocationRequest request = new CourierLocationRequest();
        request.setCourierId("123");
        request.setLatitude(41.0);
        request.setLongitude(29.0);
        request.setTime(LocalDateTime.now());

        when(storeRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        courierLocationService.saveCourierLocation(request);

        // Assert
        verify(courierLocationRepository, times(1)).save(any());
        verify(storeEntrySubject, never()).notifyObservers(anyString(), anyString(), any());
    }

    @Test
    void getTotalTravelDistanceShouldReturnCorrectDistance() {
        // Arrange
        List<CourierLocation> locations = List.of(
                new CourierLocation(1L, "123", 41.0, 29.0, LocalDateTime.now()),
                new CourierLocation(2L, "123", 41.0005, 29.0005, LocalDateTime.now().plusMinutes(5))
        );

        when(courierLocationRepository.findByCourierId("123")).thenReturn(locations);

        // Mock distance calculation
        try (MockedStatic<LocationUtils> mockedUtils = mockStatic(LocationUtils.class)) {
            mockedUtils.when(() -> LocationUtils.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                    .thenReturn(50.0); // Mock distance as 50m

            // Act
            double totalDistance = courierLocationService.getTotalTravelDistance("123");

            // Assert
            assertEquals(50.0, totalDistance);
        }
    }
}

