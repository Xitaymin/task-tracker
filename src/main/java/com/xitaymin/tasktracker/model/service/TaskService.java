package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Task;

import java.util.Collection;

public interface TaskService {

    Task saveTask(Task task);

    Task getTask(long id);

    Collection<Task> getTasks();

    void editTask(Task task);
}
