package com.xitaymin.tasktracker.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xitaymin.tasktracker.dao.entity.Project;

import javax.validation.constraints.NotBlank;

public class CreateProjectTO {
    @NotBlank
    private final String name;

    @JsonCreator
    public CreateProjectTO(@NotBlank String name) {
        this.name = name;
    }

    public Project convertToEntity() {
        Project project = new Project();
        project.setName(name);
        return project;
    }

}
