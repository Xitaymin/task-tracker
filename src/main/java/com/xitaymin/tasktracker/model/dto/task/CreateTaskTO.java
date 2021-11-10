package com.xitaymin.tasktracker.model.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

//todo found out where validation annotations should be
public class CreateTaskTO {
    @NotBlank
    private final String title;
    @NotBlank
    private final String description;
    @Positive
    private final long reporter;
    private final Long assignee;
    @Positive
    private final long project_id;
    @NotNull
    private final TaskType type;

    @JsonCreator
    public CreateTaskTO(@JsonProperty("title") @NotBlank String title,
                        @JsonProperty("description") @NotBlank String description,
                        @JsonProperty("reporter") @Positive long reporter, @JsonProperty("assignee") Long assignee,
                        @JsonProperty("project") @Positive long project_id, @JsonProperty("type") TaskType type) {
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.project_id = project_id;
        this.type = type;
    }

    //todo found out how to reduce duplication in dto

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getReporter() {
        return reporter;
    }

    public Long getAssignee() {
        return assignee;
    }

    public long getProject_id() {
        return project_id;
    }

    public TaskType getType() {
        return type;
    }

    public Task convertToEntity() {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setReporter(reporter);
        task.setAssignee(assignee);
        task.setType(type);
        return task;
    }


}
