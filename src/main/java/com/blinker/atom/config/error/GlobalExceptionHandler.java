package com.blinker.atom.config.error;

import com.blinker.atom.config.CodeValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = "Validation failed: " + e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse("Invalid input");
        return createErrorResponse(message, CodeValue.BAD_REQUEST.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SessionAuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(SessionAuthenticationException e) {
        return createErrorResponse("Authentication failed", CodeValue.NO_TOKEN_IN_REQUEST.getValue(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        return createErrorResponse(e.getMessage(), e.getCode().getValue(), e.getData(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileFailureException.class)
    public ResponseEntity<Object> handleFileFailureException(FileFailureException e) {
        return createErrorResponse(e.getMessage(), CodeValue.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return createErrorResponse("Unexpected error occurred", CodeValue.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> createErrorResponse(String message, String code, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ErrorResponseBody(message, code, status.value()));
    }

    private ResponseEntity<Object> createErrorResponse(String message, String code, Object data, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ErrorResponseBodyWithData(message, code, status.value(), data));
    }

    // 에러 응답 객체
    private record ErrorResponseBody(String message, String code, int status) {}

    // 데이터 포함 에러 응답 객체
    private record ErrorResponseBodyWithData(String message, String code, int status, Object data) {}
}