package com.xitaymin.tasktracker.model.dto.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EditTeamTO {
    @Positive
    private final long id;
    @NotNull
    private final String name;

    @JsonCreator
    public EditTeamTO(@JsonProperty("id") @Positive long id, @JsonProperty("name") @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
