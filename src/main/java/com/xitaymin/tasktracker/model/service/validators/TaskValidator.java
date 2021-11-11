package com.xitaymin.tasktracker.model.service.validators;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.dto.task.CreateTaskTO;

public interface TaskValidator {

    void validateForUpdate(Task task);

    void validateForSave(CreateTaskTO task);
}
