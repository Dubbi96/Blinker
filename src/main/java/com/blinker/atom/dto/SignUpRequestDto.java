package com.blinker.atom.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String username;
    private String password;
    private String role; // role을 무시
}