package com.xitaymin.tasktracker.model.service.validators;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;

public interface UserWithTasksValidator {

    boolean isAssigneeValid(long userId, long taskId);

    void validateToAssign(User assignee, Task task);
}
