package com.xitaymin.tasktracker.dao.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, MANAGER, LEAD, DEVELOPER;

    @Override
    public String getAuthority() {
        return name();
    }
}
