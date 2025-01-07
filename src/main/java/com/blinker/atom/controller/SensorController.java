package com.blinker.atom.controller;

import com.blinker.atom.dto.SensorDto;
import com.blinker.atom.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    /**
     * 신호기 목록 조회 API
     * @return 신호기 데이터 목록
     */
    @GetMapping
    public ResponseEntity<List<SensorDto>> getAllSensors() {
        log.info("전체 조회 : /api/sensors");
        return ResponseEntity.ok(sensorService.getAllSensors());
    }

    /**
     * 신호기 목록 조회 API
     * @return 신호기 데이터 목록
     */
    @GetMapping("/detail")
    public ResponseEntity<List<Map<String, Object>>> getSensorDetailList() {
        log.info("디테일 조회 : /api/sensors/detail");
        return ResponseEntity.ok(sensorService.getAllSensorDetail());
    }

}