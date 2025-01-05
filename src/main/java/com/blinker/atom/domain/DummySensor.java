package com.blinker.atom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dummy_sensor")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DummySensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dummy_sensor_id")
    private Long dummySensorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private SensorGroup group;

    @Column(name = "sensor_key", nullable = false, length = 50)
    private String sensorKey;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "label", length = 100)
    private String label;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}