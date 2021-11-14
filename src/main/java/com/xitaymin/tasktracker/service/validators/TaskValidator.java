package com.xitaymin.tasktracker.service.validators;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dto.task.CreateTaskTO;

public interface TaskValidator {

    Task getTaskValidForSave(CreateTaskTO task);
}
