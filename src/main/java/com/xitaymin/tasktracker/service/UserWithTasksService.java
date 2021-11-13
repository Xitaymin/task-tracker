package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dto.user.UserWithTasksAndTeamsTO;

public interface UserWithTasksService {
    void assignTask(long userId, long taskId);

    UserWithTasksAndTeamsTO getById(long id);

}
