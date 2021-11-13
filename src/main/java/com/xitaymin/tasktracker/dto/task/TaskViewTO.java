package com.xitaymin.tasktracker.dto.task;

import com.xitaymin.tasktracker.dao.entity.TaskType;

public class TaskViewTO {
    private final long id;
    private final String title;
    private final String description;
    private final long reporter;
    private final Long assignee;
    private final long projectId;
    private final TaskType type;


    public TaskViewTO(long id, String title, String description, long reporter, Long assignee, long projectId,
                      TaskType type) {
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
}
