package com.mng.couriertracking.utils;

public class LocationUtils {
    private static final double EARTH_RADIUS = 6371.0;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c * 1000;
    }

    public static boolean isWithinDistance(double courierLat, double courierLon, double storeLat, double storeLon, double threshold) {
        double distance = calculateDistance(courierLat, courierLon, storeLat, storeLon);
        return distance <= threshold;
    }
}
