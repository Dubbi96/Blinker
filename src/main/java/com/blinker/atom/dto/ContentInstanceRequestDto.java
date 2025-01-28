package com.blinker.atom.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class ContentInstanceRequestDto {
    private String remoteCseId;
    private String containerName;
    private Content content;

    @Getter
    public static class Content {
        private String cmd;
        private String deviceNumber;
        private int deviceId;
        private int signalStrength;
        private int signalThreshold;
        private int commSignalStrength;
        private int commSignalThreshold;
        private int wireless235Strength;
        private String deviceSettings;
        private Map<String, Integer> volumeSettings;
        private Map<String, Integer> silentSettings;
        private int commInterval;
        private String faultInformation;
        private int swVersion;
        private int hwVersion;
        private int buttonCount;
        private int positionGuideCount;
        private int signalGuideCount;
        private String groupNumber;
        private int signalsInGroup;
        private int groupPositionNumber;
        private int dataType;
        private int sequenceNumber;
    }
}