package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.model.dto.user.UserWithTasksAndTeamsTO;

public interface UserWithTasksService {
    void assignTask(long userId, long taskId);

    UserWithTasksAndTeamsTO getById(long id);

}
