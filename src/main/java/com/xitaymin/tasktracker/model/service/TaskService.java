package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Task;

public interface TaskService {

    boolean saveTask(Task task);
}
