package com.blinker.atom.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 예제: username으로 사용자 조회
        if (!"testUser".equals(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 테스트용 UserDetails 반환 (실제 로직에서는 데이터베이스에서 가져옵니다)
        return org.springframework.security.core.userdetails.User.builder()
                .username("testUser")
                .password("{noop}testPassword") // {noop}: 비밀번호 암호화 없음
                .roles("USER")
                .build();
    }
}
