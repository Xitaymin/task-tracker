package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.model.dto.UserWithTasks;

public interface UserWithTasksService {
    void assignTask(long userId, long taskId);

    UserWithTasks getById(long id);

}
