package com.blinker.atom.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ParsedSensorLogDto {
    // 기본 필드들
    private String cmd; // CMD
    private String deviceNumber; // 장비번호
    private int deviceId; // 장비ID
    private int positionSignalStrength; // 위치무선세기
    private int positionSignalThreshold; // 위치무선세기기준
    private int commSignalStrength; // 신호무선세기
    private int commSignalThreshold; // 신호무선세기기준
    private int wireless235Strength; // 235무선세기
    private long serverTime; // 서버 타임
    private String deviceSettings; // 장비설정

    // 음량 설정
    private Map<String, Integer> volumeSettings; // 새소리, 귀뚜라미소리, 딩동댕, 여자, 남자, 미뉴에트, 시스템 음량 등

    // 묵음 설정
    private Map<String, Integer> silentSettings; // 여자묵음1, 여자묵음2, 남자묵음1, 남자묵음2

    // 기타 설정
    private int commInterval; // 통신간격
    private String faultInformation; // 장애정보
    private int swVersion; // SW버전
    private int hwVersion; // HW버전
    private int buttonCount; // 버튼횟수
    private int positionGuideCount; // 위치안내횟수
    private int signalGuideCount; // 신호안내횟수
    private String groupNumber; // 묶음번호
    private int signalsInGroup; // 묶음내 신호기 수
    private int groupPositionNumber; // 묶음내 번호

    // 추가 필드
    private int dataType; // 데이터 종류
    private int sequenceNumber; // 순서 번호

    // 에러 관련 필드
    private boolean parsingError; // 파싱 오류 여부
    private String errorMessage; // 에러 메시지
}