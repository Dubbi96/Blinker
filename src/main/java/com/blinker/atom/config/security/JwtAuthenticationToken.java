package com.blinker.atom.config.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private final String credentials;

    @Getter
    private final String type;

    // 인증되지 않은 객체
    public JwtAuthenticationToken(Object principal, String credentials, String type) {
        super(null);
        super.setAuthenticated(true);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
    }

    // 인증된 객체
    JwtAuthenticationToken(Object principal, String credentials, String type, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}