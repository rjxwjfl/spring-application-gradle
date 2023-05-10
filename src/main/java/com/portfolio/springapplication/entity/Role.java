package com.portfolio.springapplication.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "Administrator"),
    USER("ROLE_USER", "User"),
    GUEST("ROLE_GUEST", "Guest");

    private final String key;
    private final String sub;
}
