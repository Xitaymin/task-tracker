package com.xitaymin.tasktracker.model.service.validators.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.model.dto.project.EditProjectTO;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.validators.ProjectValidator;
import org.springframework.stereotype.Service;

@Service
public class ProjectValidatorImpl implements ProjectValidator {
    public static final String PROJECT_NOT_FOUND = "Project with id = %d doesn't exist.";
    private final ProjectDao projectDao;

    public ProjectValidatorImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    //todo fix it
    @Override
    public Project validateForUpdate(EditProjectTO editProjectTO) {
        Project project = projectDao.findById(editProjectTO.getId());
        if (project == null) {
            throw new NotFoundResourceException(String.format(PROJECT_NOT_FOUND, editProjectTO.getId()));
        }
        return project;
    }
}
