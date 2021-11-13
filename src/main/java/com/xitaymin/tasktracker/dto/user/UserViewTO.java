package com.xitaymin.tasktracker.dto.user;

import com.xitaymin.tasktracker.dao.entity.Role;

import java.util.Set;

public class UserViewTO {
    private final long id;
    private final String name;
    private final String email;
    private final boolean deleted;
    private final Set<Role> roles;

    public UserViewTO(long id, String name, String email, boolean deleted, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
        this.roles = roles;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


}
