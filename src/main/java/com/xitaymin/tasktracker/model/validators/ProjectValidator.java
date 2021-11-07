package com.xitaymin.tasktracker.model.validators;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.model.dto.EditProjectTO;

public interface ProjectValidator {
    Project validateForUpdate(EditProjectTO editProjectTO);
}
