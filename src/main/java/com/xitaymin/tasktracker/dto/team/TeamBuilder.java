package com.xitaymin.tasktracker.dto.team;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;

import java.util.Set;

public final class TeamBuilder {
    protected long id;
    private String name;

    private TeamBuilder() {
    }

    public static TeamBuilder aTeam() {
        return new TeamBuilder();
    }

    public TeamBuilder withName(String name) {
        this.name = name;
        return this;
    }


    public Team build() {
        Team team = new Team();
        team.setName(name);
        team.setId(id);
        return team;
    }
}
