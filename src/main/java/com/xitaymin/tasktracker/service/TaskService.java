package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;

import java.util.Collection;

public interface TaskService {

    TaskViewTO saveTask(CreateTaskTO task);

    Task getTask(long id);

    Collection<Task> getTasks();

    void editTask(Task task);
}
