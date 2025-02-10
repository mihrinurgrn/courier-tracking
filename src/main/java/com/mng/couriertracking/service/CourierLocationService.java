package com.mng.couriertracking.service;

import com.mng.couriertracking.dto.CourierLocationRequest;
import com.mng.couriertracking.model.CourierLocation;
import com.mng.couriertracking.model.Store;
import com.mng.couriertracking.model.StoreEntry;
import com.mng.couriertracking.repository.CourierLocationRepository;
import com.mng.couriertracking.repository.StoreEntryRepository;
import com.mng.couriertracking.repository.StoreRepository;
import com.mng.couriertracking.subject.StoreEntrySubject;
import com.mng.couriertracking.utils.LocationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourierLocationService {

    @Autowired
    CourierLocationRepository courierLocationRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreEntryRepository storeEntryRepository;

    @Autowired
    StoreEntrySubject storeEntrySubject;

    public static final double STORE_RADIUS_METERS = 100.0;

    public void saveCourierLocation(CourierLocationRequest request) {
        CourierLocation courierLocation = new CourierLocation();
        courierLocation.setCourierId(request.getCourierId());
        courierLocation.setLongitude(request.getLongitude());
        courierLocation.setLatitude(request.getLatitude());
        courierLocation.setTimestamp(request.getTime());

        courierLocationRepository.save(courierLocation);

        Store store = getStoreWithinDistance(request);

        if(store != null && !isReEntryWithinOneMinute(request,store)){
            storeEntrySubject.notifyObservers(request.getCourierId(), store.getName(), request.getTime());
        }
    }

    public double getTotalTravelDistance(String courierId) {
        List<CourierLocation> locations = courierLocationRepository.findByCourierId(courierId);

        double totalDistance = 0.0;
        CourierLocation previousLocation = null;

        for (CourierLocation currentLocation : locations) {
            if (previousLocation != null) {
                totalDistance += LocationUtils.calculateDistance(previousLocation.getLatitude(),
                        previousLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude());
            }
            previousLocation = currentLocation;
        }

        return totalDistance;
    }

    private Store getStoreWithinDistance(CourierLocationRequest courierLocationRequest) {
        List<Store> stores = storeRepository.findAll();

        for (Store store : stores) {
            boolean isWithinDistance = LocationUtils.isWithinDistance(
                    courierLocationRequest.getLatitude(),
                    courierLocationRequest.getLongitude(),
                    store.getLatitude(),
                    store.getLongitude(),
                    STORE_RADIUS_METERS
            );

            if (isWithinDistance) {
                return store;
            }
        }
        return null;
    }

    private boolean isReEntryWithinOneMinute(CourierLocationRequest courierLocationRequest, Store store) {
        LocalDateTime currentTime = courierLocationRequest.getTime();
        String courierId = courierLocationRequest.getCourierId();

        Optional<StoreEntry> previousEntrance = storeEntryRepository.findTopByCourierIdAndStoreNameOrderByEntryTimeDesc(courierId, store.getName());

        return previousEntrance.filter(storeEntry ->
                Duration.between(storeEntry.getEntryTime(), currentTime).toMinutes() <= 1).isPresent();
    }


}
