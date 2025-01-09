package com.blinker.atom.controller;

import com.blinker.atom.dto.AppUserResponseDto;
import com.blinker.atom.dto.SignInRequestDto;
import com.blinker.atom.dto.SignUpRequestDto;
import com.blinker.atom.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public String login(@RequestBody SignInRequestDto authRequest) {
        log.info("로그인 요청 받음: {}", authRequest.getUsername());
        return authService.login(authRequest);
    }

    @PostMapping("/sign-up")
    public Long signup(@RequestBody SignUpRequestDto authRequestDto) {
        log.info("회원가입 요청 받음: {}", authRequestDto); // 요청이 도달했는지 확인
        return authService.signUp(authRequestDto);
    }

    @GetMapping("/user/{id}")
    public AppUserResponseDto getUserDetails(@PathVariable Long id) {
        return authService.getUserDetails(id);
    }
}