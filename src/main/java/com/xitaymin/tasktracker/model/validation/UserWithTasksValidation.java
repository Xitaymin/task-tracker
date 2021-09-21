package com.xitaymin.tasktracker.model.validation;

public interface UserWithTasksValidation {

    boolean areUserAndTaskValidToAssign(long userId, long taskId);
}
