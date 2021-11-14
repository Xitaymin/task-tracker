package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;

import java.util.Collection;

public interface TaskService {

    TaskViewTO saveTask(CreateTaskTO task);

    TaskViewTO getTask(long id);

    Collection<TaskViewTO> getTasks();

    void editTask(Task task);
}
