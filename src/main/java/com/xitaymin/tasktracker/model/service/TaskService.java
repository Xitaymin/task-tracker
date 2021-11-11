package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.dto.task.CreateTaskTO;

import java.util.Collection;

public interface TaskService {

    Task saveTask(CreateTaskTO task);

    Task getTask(long id);

    Collection<Task> getTasks();

    void editTask(Task task);
}
