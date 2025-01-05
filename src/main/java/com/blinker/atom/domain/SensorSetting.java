package com.blinker.atom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_setting")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    private Long settingId;

    @OneToOne(fetch = FetchType.LAZY)
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

    @Column(name = "a235_signal_strength")
    private Float a235SignalStrength;

    @Column(name = "bird_volume")
    private Integer birdVolume;

    @Column(name = "cricket_volume")
    private Integer cricketVolume;

    @Column(name = "melody_volume")
    private Integer melodyVolume;

    @Column(name = "female_volume")
    private Integer femaleVolume;

    @Column(name = "male_volume")
    private Integer maleVolume;

    @Column(name = "minuet_volume")
    private Integer minuetVolume;

    @Column(name = "system_volume")
    private Integer systemVolume;

    @Column(name = "female_mute_time1")
    private Integer femaleMuteTime1;

    @Column(name = "female_mute_time2")
    private Integer femaleMuteTime2;

    @Column(name = "male_mute_time1")
    private Integer maleMuteTime1;

    @Column(name = "male_mute_time2")
    private Integer maleMuteTime2;

    @Column(name = "setting_gender")
    private Boolean settingGender;

    @Column(name = "setting_sound")
    private Boolean settingSound;

    @Column(name = "setting_crossing")
    private Boolean settingCrossing;

    @Column(name = "setting_post")
    private Integer settingPost;

    @Column(name = "setting_setting")
    private Boolean settingSetting;

    @Column(name = "setting_before_after")
    private Boolean settingBeforeAfter;

    @Column(name = "report_interval")
    private Integer reportInterval;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime updatedAt;
}