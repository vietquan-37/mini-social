package com.example.social_web.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public enum Role {
    USER,
    ADMIN;
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
