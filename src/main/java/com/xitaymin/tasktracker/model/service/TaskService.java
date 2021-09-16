package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Task;

import java.util.Collection;

public interface TaskService {

    boolean saveTask(Task task);

    Task getTask(Long id);

    Collection<Task> getTasks();

    boolean assignTask(Long userId, Long taskId);
}
