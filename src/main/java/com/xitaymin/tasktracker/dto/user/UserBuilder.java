package com.xitaymin.tasktracker.dto.user;

import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;

import java.util.Set;

public final class UserBuilder {
    protected long id;
    private String name;
    private String email;
    private boolean deleted;
    private Set<Role> roles;
    private Set<Task> tasks;
    private Team team;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public UserBuilder withRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder withTasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public UserBuilder withTeam(Team team) {
        this.team = team;
        return this;
    }

    public UserBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public User build() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setDeleted(deleted);
        user.setRoles(roles);
        user.setTeam(team);
        return user;
    }
}
