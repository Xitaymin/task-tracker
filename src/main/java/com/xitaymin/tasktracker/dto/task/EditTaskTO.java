package com.xitaymin.tasktracker.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class EditTaskTO {
    @Positive
    private final long id;
    @NotBlank
    private final String title;
    @NotBlank
    private final String description;

    @JsonCreator
    public EditTaskTO(@JsonProperty("id") @Positive long id, @JsonProperty("title") @NotBlank String title,
                      @JsonProperty("description") @NotBlank String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
