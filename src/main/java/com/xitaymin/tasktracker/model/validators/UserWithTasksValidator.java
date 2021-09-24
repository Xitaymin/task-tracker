package com.xitaymin.tasktracker.model.validators;

public interface UserWithTasksValidator {

    boolean areUserAndTaskValidToAssign(long userId, long taskId);
}
