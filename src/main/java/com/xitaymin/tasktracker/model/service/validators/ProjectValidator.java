package com.xitaymin.tasktracker.model.service.validators;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.model.dto.project.EditProjectTO;

public interface ProjectValidator {
    Project validateForUpdate(EditProjectTO editProjectTO);

}
