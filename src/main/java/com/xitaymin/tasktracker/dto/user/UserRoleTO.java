package com.xitaymin.tasktracker.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xitaymin.tasktracker.dao.entity.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserRoleTO {
    @Positive
    private final long id;
    @NotNull
    private final Role role;

    @JsonCreator
    public UserRoleTO(@JsonProperty("id") @Positive long id, @JsonProperty("role") @NotNull Role role) {
        this.id = id;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
