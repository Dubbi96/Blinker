package com.blinker.atom.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_setting")
@Data
public class SensorSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    private Long settingId;

    @OneToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "location_guide_signal_strength")
    private Float locationGuideSignalStrength;

    @Column(name = "location_guide_signal_strength_standard")
    private Float locationGuideSignalStrengthStandard;

    @Column(name = "signal_guide_signal_strength")
    private Float signalGuideSignalStrength;

    @Column(name = "signal_guide_signal_strength_standard")
    private Float signalGuideSignalStrengthStandard;

    @Column(name = "bird_volume")
    private Integer birdVolume;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}