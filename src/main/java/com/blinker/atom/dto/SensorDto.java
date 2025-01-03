package com.blinker.atom.dto;

import com.blinker.atom.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDto {
    private String address;
    private double latitude;
    private double longitude;
    private String status;

    // 추가: Sensor 엔터티에서 DTO로 변환하는 생성자
    public SensorDto(Sensor sensor) {
        this.address = sensor.getAddress();
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
        this.status = sensor.getStatus();
    }
}