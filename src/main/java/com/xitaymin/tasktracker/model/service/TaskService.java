package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.model.dto.task.TaskViewTO;

import java.util.Collection;

public interface TaskService {

    TaskViewTO saveTask(CreateTaskTO task);

    Task getTask(long id);

    Collection<Task> getTasks();

    void editTask(Task task);
}
