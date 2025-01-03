package com.blinker.atom.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_group")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_group_id")
    private Long userGroupId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private SensorGroup group;

    @Column(name = "role", length = 20)
    private String role = "MEMBER";

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}