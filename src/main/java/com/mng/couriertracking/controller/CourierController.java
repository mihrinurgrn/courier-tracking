package com.mng.couriertracking.controller;

import com.mng.couriertracking.dto.CourierLocationRequest;
import com.mng.couriertracking.service.CourierLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    @Autowired
    CourierLocationService courierLocationService;

    @PostMapping("/update-courier-location")
    public ResponseEntity<String> updateCourierLocation(@RequestBody CourierLocationRequest request) {
        courierLocationService.saveCourierLocation(request);
        return new ResponseEntity<>("Courier location updated successfully", HttpStatus.OK);
    }

    @GetMapping("/courier/{courierId}/total-distance")
    public double getTotalTravelDistance(@PathVariable String courierId) {
        return courierLocationService.getTotalTravelDistance(courierId);
    }
}
