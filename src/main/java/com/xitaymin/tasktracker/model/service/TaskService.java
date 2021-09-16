package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Task;

import java.util.Collection;

public interface TaskService {

    void saveTask(Task task);

    Task getTask(Long id);

    Collection<Task> getTasks();

    void assignTask(Long userId, Long taskId);

    void editTask(Task task);
}
