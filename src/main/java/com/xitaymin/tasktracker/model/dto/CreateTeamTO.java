package com.xitaymin.tasktracker.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xitaymin.tasktracker.dao.entity.Team;

import javax.validation.constraints.NotNull;

public class CreateTeamTO {

    @NotNull
    private final String name;

    @JsonCreator
    public CreateTeamTO(@JsonProperty("name") @NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Team convertToEntity() {
        Team team = new Team();
        team.setName(name);
        return team;
    }
}
