package com.mng.couriertracking.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LocationUtilsTest {

    @Test
    void calculateDistanceShouldReturnCorrectDistanceWhenGivenValidCoordinates() {
        // Arrange
        double lat1 = 41.0;
        double lon1 = 29.0;
        double lat2 = 41.0005;
        double lon2 = 29.0005;

        // Act
        double distance = LocationUtils.calculateDistance(lat1, lon1, lat2, lon2);

        // Assert
        double expectedDistance = 69.65;
        assertEquals(expectedDistance, distance, 1.0);
    }

    @Test
    void isWithinDistanceShouldReturnTrueWhenDistanceIsLessThanThreshold() {
        // Arrange
        double courierLat = 41.0;
        double courierLon = 29.0;
        double storeLat = 41.0005;
        double storeLon = 29.0005;
        double threshold = 100.0; // 100 metre

        // Act
        boolean result = LocationUtils.isWithinDistance(courierLat, courierLon, storeLat, storeLon, threshold);

        // Assert
        assertTrue(result, "Mesafe eşik değeri içinde olmalı.");
    }

    @Test
    void isWithinDistanceShouldReturnFalseWhenDistanceIsGreaterThanThreshold() {
        // Arrange
        double courierLat = 41.0;
        double courierLon = 29.0;
        double storeLat = 41.1; // Uzak nokta
        double storeLon = 29.1;
        double threshold = 100.0; // 100 metre

        // Act
        boolean result = LocationUtils.isWithinDistance(courierLat, courierLon, storeLat, storeLon, threshold);

        // Assert
        assertFalse(result, "Mesafe eşik değerinin dışında olmalı.");
    }
}

