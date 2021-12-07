package com.xitaymin.tasktracker.web.dto;

import com.xitaymin.tasktracker.dao.entity.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AdminTaskView {
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


}
