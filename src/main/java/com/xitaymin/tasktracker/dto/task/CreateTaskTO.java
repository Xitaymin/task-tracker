package com.xitaymin.tasktracker.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreateTaskTO {
    @NotBlank
    private final String title;
    @NotBlank
    private final String description;
    @Positive
    private final long reporter;
    private final Long assignee;
    @Positive
    private final long projectId;
    @NotNull
    private final TaskType type;
    private final Long parentId;

    @JsonCreator
    public CreateTaskTO(@JsonProperty("title") @NotBlank String title,
                        @JsonProperty("description") @NotBlank String description,
                        @JsonProperty("reporter") @Positive long reporter, @JsonProperty("assignee") Long assignee,
                        @JsonProperty("project") @Positive long projectId, @JsonProperty("type") TaskType type,
                        @JsonProperty("parent") Long parentId) {
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.projectId = projectId;
        this.type = type;
        this.parentId = parentId;
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

    public long getProjectId() {
        return projectId;
    }

    public TaskType getType() {
        return type;
    }

    public Long getParentId() {
        return parentId;
    }

    public Task convertToEntity() {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setType(type);
        return task;
    }


}
