package com.blinker.atom.utils;

import com.blinker.atom.dto.ContentInstanceRequestDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class EncodingUtil {

    public static String encodeToHex(ContentInstanceRequestDto contentInstanceRequestDto) {
        if (contentInstanceRequestDto == null || contentInstanceRequestDto.getContent() == null) {
            throw new IllegalArgumentException("Invalid request: content is null");
        }

        ContentInstanceRequestDto.Content content = contentInstanceRequestDto.getContent();
        StringBuilder hexString = new StringBuilder();

        // 1. CMD (2자리)
        hexString.append(String.format("%02x", Integer.parseInt(content.getCmd(), 16)));

        // 2. Device Number (8자리)
        hexString.append(content.getDeviceNumber());

        // 3. Device ID (2자리)
        hexString.append(String.format("%02x", content.getDeviceId()));

        // 4. Signal Strength (2자리)
        hexString.append(String.format("%02x", content.getSignalStrength()));

        // 5. Signal Threshold (2자리)
        hexString.append(String.format("%02x", content.getSignalThreshold()));

        // 6. Communication Signal Strength (2자리)
        hexString.append(String.format("%02x", content.getCommSignalStrength()));

        // 7. Communication Signal Threshold (2자리)
        hexString.append(String.format("%02x", content.getCommSignalThreshold()));

        // 8. Wireless Strength (2자리)
        hexString.append(String.format("%02x", content.getWireless235Strength()));

        // 9. Server Time (8자리)
        String serverTimeHex = String.format("%08x", (System.currentTimeMillis() / 1000) & 0xFFFFFFFFL);
        hexString.append(serverTimeHex);

        // 10. Device Settings (2자리)
        String deviceSettingsHex = encodeDeviceSettings(content.getDeviceSettings());
        hexString.append(deviceSettingsHex);

        // 11. Volume Settings (14자리)
        String volumeSettingsHex = encodeVolumeSettings(content.getVolumeSettings());
        hexString.append(volumeSettingsHex);

        // 12. Silent Settings (8자리)
        String silentSettingsHex = encodeSilentSettings(content.getSilentSettings());
        hexString.append(silentSettingsHex);

        // 13. Communication Interval (2자리)
        hexString.append(String.format("%02x", content.getCommInterval()));

        // 14. Fault Information (2자리)
        String faultInfoHex = encodeFaultInformation(content.getFaultInformation());
        hexString.append(faultInfoHex);

        // 15. SW Version (2자리)
        String swVersionHex = encodeBigEndian(content.getSwVersion(), 2);
        hexString.append(swVersionHex);

        // 16. HW Version (2자리)
        hexString.append(String.format("%02x", content.getHwVersion()));

        // 17. Button Count (2자리)
        hexString.append(String.format("%02x", content.getButtonCount()));

        // 18. Position Guide Count (2자리)
        hexString.append(String.format("%02x", content.getPositionGuideCount()));

        // 19. Signal Guide Count (2자리)
        hexString.append(String.format("%02x", content.getSignalGuideCount()));

        // 20. Group Number (8자리 - Little-Endian 처리)
        hexString.append(content.getGroupNumber());

        // 21. Signals in Group (2자리)
        hexString.append(String.format("%02x", content.getSignalsInGroup()));

        // 22. Group Position Number (2자리)
        hexString.append(String.format("%02x", content.getGroupPositionNumber()));

        // 23. Data Type (2자리)
        hexString.append(String.format("%02x", content.getDataType()));

        // 24. Sequence Number (2자리)
        hexString.append(String.format("%02x", content.getSequenceNumber()));

        int paddingLength = 102 - hexString.length() - 2;
        if (paddingLength < 0) {
            throw new IllegalStateException("Encoded data exceeds 102 characters");
        }

        String padding = "0".repeat(paddingLength);
        hexString.append(padding);
        log.debug("Added Padding: {}", padding);

        String checksum = calculateChecksum(hexString.toString());
        hexString.append(checksum);
        log.debug("Encoded Checksum: {}", checksum);

        if (hexString.length() != 102) {
            throw new IllegalStateException("Final encoded data length is not 102: " + hexString.length());
        }

        return hexString.toString().toLowerCase();
    }

    private static String encodeDeviceSettings(String settings) {
        int deviceSettingValue = 0;
        if (settings.contains("Female")) deviceSettingValue |= 1;
        if (settings.contains("Bird")) deviceSettingValue |= 2;
        if (settings.contains("Intersection")) deviceSettingValue |= 4;
        if (settings.contains("Close Proximity")) deviceSettingValue |= 8;
        if (settings.contains("Single Proximity")) deviceSettingValue |= 16;
        if (settings.contains("Configured")) deviceSettingValue |= 32;
        if (settings.contains("Female Priority Broadcast")) deviceSettingValue |= 64;
        return String.format("%02x", deviceSettingValue);
    }

    private static String encodeVolumeSettings(Map<String, Integer> volumeSettings) {
        if (volumeSettings == null || volumeSettings.isEmpty()) {
            return "00000000000000";
        }
        return String.format("%02x%02x%02x%02x%02x%02x%02x",
                volumeSettings.getOrDefault("Bird Volume", 0),
                volumeSettings.getOrDefault("Cricket Volume", 0),
                volumeSettings.getOrDefault("Dingdong Volume", 0),
                volumeSettings.getOrDefault("Female Volume", 0),
                volumeSettings.getOrDefault("Male Volume", 0),
                volumeSettings.getOrDefault("Minuet Volume", 0),
                volumeSettings.getOrDefault("System Volume", 0));
    }

    private static String encodeSilentSettings(Map<String, Integer> silentSettings) {
        if (silentSettings == null || silentSettings.isEmpty()) {
            return "00000000";
        }
        return String.format("%02x%02x%02x%02x",
                silentSettings.getOrDefault("Female Mute 1", 0),
                silentSettings.getOrDefault("Female Mute 2", 0),
                silentSettings.getOrDefault("Male Mute 1", 0),
                silentSettings.getOrDefault("Male Mute 2", 0));
    }

    private static String encodeFaultInformation(String faultInformation) {
        int faultValue = 0;
        if (faultInformation.contains("Front Cover Open")) faultValue |= 1;
        if (faultInformation.contains("235.3MHz Receiver Fault")) faultValue |= 2;
        if (faultInformation.contains("358.5MHz Receiver Fault")) faultValue |= 4;
        if (faultInformation.contains("User Button Fault")) faultValue |= 8;
        if (faultInformation.contains("Speaker Fault")) faultValue |= 16;
        if (faultInformation.contains("Signal Light Residual Fault")) faultValue |= 32;
        return String.format("%02x", faultValue);
    }

    private static String calculateChecksum(String hexData) {
        int sum = 0;
        for (int i = 0; i < hexData.length(); i += 2) {
            sum += Integer.parseInt(hexData.substring(i, i + 2), 16);
        }
        return String.format("%02x", sum & 0xFF);
    }

    private static String encodeBigEndian(int value, int byteLength) {
        StringBuilder result = new StringBuilder();
        for (int i = (byteLength - 1) * 8; i >= 0; i -= 8) {
            result.append(String.format("%02x", (value >> i) & 0xFF));
        }
        return result.toString();
    }

}
