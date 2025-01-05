package com.blinker.atom.service;

import com.blinker.atom.config.error.ErrorValue;
import com.blinker.atom.config.security.JwtProvider;
import com.blinker.atom.domain.AppUser;
import com.blinker.atom.domain.AppUserRepository;
import com.blinker.atom.domain.Role;
import com.blinker.atom.dto.AppUserResponseDto;
import com.blinker.atom.dto.SignInRequestDto;
import com.blinker.atom.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public String login(SignInRequestDto authRequest) {
        AppUser user = appUserRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        if (!passwordEncoder.matches(authRequest.getPassword() + user.getSalt(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRoles());

        return jwtProvider.createToken(claims);
    }

    @Transactional
    public Long signUp(SignUpRequestDto authRequestDto) {
        appUserRepository.findByUsername(authRequestDto.getUsername()).ifPresent(member -> {
            throw new IllegalArgumentException(ErrorValue.NICKNAME_ALREADY_EXISTS.toString());
        });
        String salt = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(authRequestDto.getPassword() + salt);

        Role userRole = Role.valueOf(authRequestDto.getRole().toString().toUpperCase()); // String을 Role enum으로 변환

        AppUser newUser = AppUser.builder()
                .username(authRequestDto.getUsername())
                .email(null) // email 받으라 하면 추가
                .password(encodedPassword)
                .salt(salt)
                .roles(Collections.singletonList(userRole))
                .isActive(true)
                .build();

        appUserRepository.save(newUser);
        return newUser.getUserId();
    }

    public AppUserResponseDto getUserDetails(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        AppUserResponseDto userResponse = new AppUserResponseDto();
        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRoles());
        return userResponse;
    }
}