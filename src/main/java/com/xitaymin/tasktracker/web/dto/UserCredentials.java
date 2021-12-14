package com.xitaymin.tasktracker.web.dto;

import com.xitaymin.tasktracker.dao.entity.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class UserCredentials implements UserDetails {

    public final int id;
    public final String email;
    private final String password;
    private final Set<Role> roles;

    public UserCredentials(int id, String email, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Set<Role> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
