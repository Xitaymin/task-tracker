package com.xitaymin.tasktracker.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EditProjectTO {
    @NotNull
    @Positive
    private final long id;
    @NotBlank
    private final String name;

    @JsonCreator
    public EditProjectTO(@NotNull @Positive long id, @NotBlank String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //    public Project convertToEntity(){
//        Project project = new Project();
//        project.setId(id);
//        project.setName(name);
//        return project;
//    }
}
