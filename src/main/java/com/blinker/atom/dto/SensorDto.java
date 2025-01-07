package com.blinker.atom.dto;

import com.blinker.atom.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDto {
    private Long sensorId;
    private String address;
    private double latitude;
    private double longitude;
    private String status;
    private int buttonClickCount;
    private int locationGuideCount;
    private int signalGuideCount;
    private LocalDateTime createdAt;

    public SensorDto(Sensor sensor) {
        this.sensorId = sensor.getSensorId();
        this.address = sensor.getAddress();
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
        this.status = sensor.getStatus();
        this.buttonClickCount = sensor.getButtonClickCount();
        this.locationGuideCount = sensor.getLocationGuideCount();
        this.signalGuideCount = sensor.getSignalGuideCount();
        this.createdAt = sensor.getCreatedAt();
    }
}