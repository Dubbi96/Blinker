package com.blinker.atom.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_log")
@Data
public class SensorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "event_type", length = 50)
    private String eventType;

    @Column(name = "event_details", columnDefinition = "jsonb")
    private String eventDetails;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}