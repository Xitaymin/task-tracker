package com.xitaymin.tasktracker.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xitaymin.tasktracker.dao.entity.TaskType;

import java.util.Objects;

public class TaskViewTO {
    private final long id;
    private final String title;
    private final String description;
    private final long reporter;
    private final Long assignee;
    private final long projectId;
    private final TaskType type;

    @JsonCreator
    public TaskViewTO(@JsonProperty("id") long id, @JsonProperty("title") String title,
                      @JsonProperty("description") String description, @JsonProperty("reporter") long reporter,
                      @JsonProperty("assignee") Long assignee, @JsonProperty("project") long projectId,
                      @JsonProperty("type") TaskType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.projectId = projectId;
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewTO that = (TaskViewTO) o;
        return reporter == that.reporter && projectId == that.projectId && title.equals(that.title) && description.equals(
                that.description) && Objects.equals(assignee, that.assignee) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, reporter, assignee, projectId, type);
    }
}


