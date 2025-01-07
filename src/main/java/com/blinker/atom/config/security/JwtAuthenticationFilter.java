package com.blinker.atom.config.security;

import com.blinker.atom.config.Value;
import com.blinker.atom.domain.AppUser;
import com.blinker.atom.domain.AppUserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Slf4j
@Component
@RequiredArgsConstructor
/*
    Request가 Controller까지 도착하기 전, 필터에서 헤더를 체크하여 access token이 있다면 검증을 진행한다.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AppUserRepository appUserRepository;

    /*@Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldNotFilter = path.startsWith("/webapp/") || path.startsWith("/css/") ||
                   path.startsWith("/js/") || path.startsWith("/images/") ||
                   path.endsWith(".jsp") || path.startsWith("/auth/");
        log.debug("shouldNotFilter: path = {}, result = {}", path, shouldNotFilter);
        return shouldNotFilter;
    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 인증된 사용자 정보가 이미 설정된 경우 체인 진행
        if (nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            chain.doFilter(request, response);
            return;
        }

        // 헤더에서 토큰 추출
        String jwtToken = request.getHeader(Value.ACCESS_TOKEN_HEADER_KEY);
        if (isNull(jwtToken) || !jwtToken.startsWith("Bearer ")) {
            log.debug("JWT 토큰이 없거나 유효하지 않음: {}", jwtToken);
            chain.doFilter(request, response);
            return;
        }

        jwtToken = jwtToken.substring(7); // "Bearer " 이후의 토큰 값 추출
        try {
            Claims claims = jwtProvider.parseToken(jwtToken);
            log.info("토큰 검증 성공: '{}'",jwtToken);
            JwtAuthenticationToken authenticationToken = authenticate(claims);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            log.error("토큰 검증 실패: '{}'", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return; // 필터 체인 중단
        }

        chain.doFilter(request, response);
    }

    private JwtAuthenticationToken authenticate(Claims claims) {
        if (claims.get("userId") != null) {
            AppUser appUser = appUserRepository.findById(Long.parseLong(claims.get("userId").toString())).orElseThrow(IllegalArgumentException::new);
            TokenPrincipal principal = new TokenPrincipal(appUser);
            List<SimpleGrantedAuthority> roles = appUser.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.name()))
                .toList();
            return new JwtAuthenticationToken(principal, null, "user", roles);
        }
        throw new IllegalArgumentException("유효하지 않은 토큰 : 인증된 사용자 타입이 아닙니다.");
    }
}