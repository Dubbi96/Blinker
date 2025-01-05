package com.blinker.atom.config.security;

import com.blinker.atom.domain.AppUser;
import lombok.Getter;

import javax.security.auth.Subject;
import java.security.Principal;

@Getter
public class TokenPrincipal implements Principal {

    private final AppUser user;

    public TokenPrincipal(AppUser user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
