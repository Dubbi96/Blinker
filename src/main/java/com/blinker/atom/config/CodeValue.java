package com.blinker.atom.config;

import lombok.Getter;

@Getter
public enum CodeValue {
    SUCCESS("SUCCESS"),
    FAILURE_MODAL("DIALOGUE"), // 해당 코드를 리턴하면 프론트에서는 다이얼로그로 띄워주는 핸들링을 한다.
    /*
    이후는 각각의 경우에 대해서 다이얼로그를 제외한 핸들링을 한다.
     */
    BAD_REQUEST("BR001"),
    NO_TOKEN_IN_REQUEST("A001"), // 토큰 필요한 API에서 토큰 검증시 request header에 토큰이 없는 경우
    NO_TOKEN("A002"), // 레디스에 토큰이 없는 경우 / 토큰이 만료되거나 등으로 없는 경우
    ACCESS_DENIED("A003"), //권한이 충분하지 않은 경우
    ALREADY_APPLICANT_EXISTS("U001"),
    ALREADY_SURVEY_DONE("S001"),
    DATE_END("D001"),
    DATE_SET_ERROR("D002"),
    EMPTY_FILES("EM001"),
    NO_USER_FROM_TOKEN("U002"), // 토큰으로 파싱한 userId가 DB에 존재하지 않는 경우
    NO_USER_FROM_EMAIL("U003"), // 이메일에 해당하는 유저를 찾을 수 없는 경우
    NO_PAGE("P001"), // 페이지가 존재하지 않는 경우
    INTERNAL_ERROR("E001"),
    ALREADY_ADDITIONAL_DOC_SUBMITTED("AD001");
    private final String value;

    CodeValue(String value) {
        this.value = value;
    }
}
