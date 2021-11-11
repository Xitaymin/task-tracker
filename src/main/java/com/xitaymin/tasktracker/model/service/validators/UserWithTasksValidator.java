package com.xitaymin.tasktracker.model.service.validators;

public interface UserWithTasksValidator {

    boolean areUserAndTaskValidToAssign(long userId, long taskId);
}
