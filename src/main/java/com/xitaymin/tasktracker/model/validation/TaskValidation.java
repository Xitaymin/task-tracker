package com.xitaymin.tasktracker.model.validation;

import com.xitaymin.tasktracker.dao.entity.Task;

public interface TaskValidation {

    boolean isTaskValidForUpdate(Task task);

    boolean isTaskValidForSave(Task task);
}
