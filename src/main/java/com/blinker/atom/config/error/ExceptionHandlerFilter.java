package com.blinker.atom.config.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e.getMessage(), e.getCode().getValue());
        } catch (RuntimeException e) {
            log.error("Spring Security Filter에서 에러가 발생하였습니다. (msg : " + e.getMessage() + ")", e);
            setErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    response,
                    "Unknown error occurred.",
                    "INTERNAL_ERROR"
            );
        }
    }

    private void setErrorResponse(HttpStatus status, HttpServletResponse response, String message, String code) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            String json = objectMapper.writeValueAsString(
                    new ResponseBody(message, code, status.value())
            );
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            log.error("Error writing response", e);
        }
    }

    // 응답 객체
    private record ResponseBody(String message, String code, int status) {}
}