package com.xitaymin.tasktracker.service.validators;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;

public interface ProjectValidator {
    Project validateForUpdate(EditProjectTO editProjectTO);

}
