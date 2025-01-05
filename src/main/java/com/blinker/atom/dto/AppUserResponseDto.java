package com.blinker.atom.dto;

import com.blinker.atom.domain.Role;
import lombok.Data;

import java.util.List;

@Data
public class AppUserResponseDto {
    private Long userId;
    private String username;
    private String email;
    private List<Role> role;
    private boolean isActive;
}