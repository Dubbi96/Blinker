package com.blinker.atom.service;

import com.blinker.atom.domain.SensorRepository;
import com.blinker.atom.dto.SensorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    public List<SensorDto> getAllSensors() {
        return sensorRepository.findAll()
                               .stream()
                               .map(SensorDto::new)
                               .toList();
    }

    public List<Map<String, Object>> getAllSensorDetail() {
        return sensorRepository.findAll()
                               .stream()
                               .map(sensor -> {
                                   Map<String, Object> signalData = new HashMap<>();
                                   signalData.put("sensorId", sensor.getSensorId());
                                   signalData.put("address", sensor.getAddress());
                                   signalData.put("buttonClickCount", sensor.getButtonClickCount());
                                   signalData.put("locationGuideCount", sensor.getLocationGuideCount());
                                   signalData.put("signalGuideCount", sensor.getSignalGuideCount());
                                   signalData.put("createdAt", sensor.getCreatedAt());
                                   signalData.put("status", sensor.getStatus());
                                   return signalData;
                               })
                               .toList();
    }
}