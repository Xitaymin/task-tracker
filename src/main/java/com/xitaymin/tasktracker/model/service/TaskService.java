package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Task;

import java.util.Collection;

public interface TaskService {

    Task saveTask(Task task);

    Task getTask(Long id);

    Collection<Task> getTasks();

    Task assignTask(Long userId, Long taskId);

    Task editTask(Task task);
}
