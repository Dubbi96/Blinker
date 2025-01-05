package com.blinker.atom.config.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@SuppressWarnings({"rawtypes"})
public class ApiResponse<T> {

    private String message;
    private String code;
    private T response;

    @Builder
    public ApiResponse(String message, String code, T response) {
        this.message = message;
        this.code = code;
        this.response = response;
    }

    public ApiResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public static ResponseEntity<ApiResponse> makeErrorResponse(String message, String code, Object object) {
        ApiResponse response = ApiResponse.builder()
                .message(message)
                .code(code)
                .response(object)
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ApiResponse> makeErrorResponse(String message, String code) {
        ApiResponse response = ApiResponse.builder()
                .message(message)
                .code(code)
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ApiResponse> makeErrorResponse(String message, String code, HttpStatus status) {
        ApiResponse response = ApiResponse.builder()
                .message(message)
                .code(code)
                .build();
        return ResponseEntity.status(status.value()).body(response);
    }
}
