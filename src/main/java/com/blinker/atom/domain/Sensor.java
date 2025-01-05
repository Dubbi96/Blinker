package com.blinker.atom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Long sensorId;

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

    @Column(name = "is_fault")
    private Boolean isFault;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "button_click_count", columnDefinition = "INT DEFAULT 0")
    private Integer buttonClickCount;

    @Column(name = "location_guide_count", columnDefinition = "INT DEFAULT 0")
    private Integer locationGuideCount;

    @Column(name = "signal_guide_count", columnDefinition = "INT DEFAULT 0")
    private Integer signalGuideCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}