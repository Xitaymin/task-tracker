package com.xitaymin.tasktracker.model.validators;

import com.xitaymin.tasktracker.dao.entity.Task;

public interface TaskValidator {

    void validateForUpdate(Task task);

    void validateForSave(Task task);
}
