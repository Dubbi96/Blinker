package com.blinker.atom.config.security;

import com.blinker.atom.domain.AppUser;

import javax.security.auth.Subject;
import java.security.Principal;

public record TokenPrincipal(AppUser user) implements Principal {

    @Override
    public String getName() {
        return user.getUserId();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
