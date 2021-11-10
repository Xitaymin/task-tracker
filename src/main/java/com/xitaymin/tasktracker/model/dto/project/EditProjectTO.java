package com.xitaymin.tasktracker.model.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class EditProjectTO {
    @Positive
    private final long id;
    @NotBlank
    private final String name;

    @JsonCreator
    public EditProjectTO(@JsonProperty("id") @Positive long id, @JsonProperty("name") @NotBlank String name) {
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
