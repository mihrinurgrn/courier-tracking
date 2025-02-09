package com.mng.couriertracking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourierLocationRequest {
    private String courierId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime time;
}
