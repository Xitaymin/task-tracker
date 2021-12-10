package com.xitaymin.tasktracker.web.dto;

import com.xitaymin.tasktracker.dao.entity.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AdminTaskView {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    //    @Positive
//    private  long reporter;
//    private  Long assignee;
    @Positive
    private long projectId;
    @NotNull
    private TaskType type;
//    private  Long parentId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
}
