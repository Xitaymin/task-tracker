package com.xitaymin.tasktracker.model.dto.user;

import com.xitaymin.tasktracker.dao.entity.Role;

import java.util.Set;

public class UserWithTasksAndTeamsTO {
    private final long id;
    private final String name;
    private final String email;
    private final boolean deleted;
    private final Set<Role> roles;
    private final Set<Long> tasksId;
    private final Long teamId;

    public UserWithTasksAndTeamsTO(long id, String name, String email, boolean deleted, Set<Role> roles,
                                   Set<Long> tasksId, Long teamId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
        this.roles = roles;
        this.tasksId = tasksId;
        this.teamId = teamId;
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

    public boolean isDeleted() {
        return deleted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Long> getTasksId() {
        return tasksId;
    }

    public Long getTeamId() {
        return teamId;
    }
}

