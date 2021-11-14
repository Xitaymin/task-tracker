package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.EditTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;

import javax.validation.Valid;
import java.util.Collection;

public interface TaskService {

    TaskViewTO saveTask(CreateTaskTO task);

    TaskViewTO getTask(long id);

    Collection<TaskViewTO> getTasks();

    void editTask(@Valid EditTaskTO task);
}
