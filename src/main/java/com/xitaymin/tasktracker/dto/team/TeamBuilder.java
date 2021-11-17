package com.xitaymin.tasktracker.dto.team;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;

import java.util.Set;

public final class TeamBuilder {
    protected long id;
    private String name;
    private Set<User> members;
    private Set<Project> projects;

    private TeamBuilder() {
    }

    public static TeamBuilder aTeam() {
        return new TeamBuilder();
    }

    public TeamBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TeamBuilder withMembers(Set<User> members) {
        this.members = members;
        return this;
    }

    public TeamBuilder withProjects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public TeamBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public Team build() {
        Team team = new Team();
        team.setName(name);
        team.setId(id);
        return team;
    }
}
