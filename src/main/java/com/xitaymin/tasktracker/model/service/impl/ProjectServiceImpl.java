package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.model.dto.CreateProjectTO;
import com.xitaymin.tasktracker.model.dto.EditProjectTO;
import com.xitaymin.tasktracker.model.service.ProjectService;
import com.xitaymin.tasktracker.model.validators.ProjectValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectValidator projectValidator;
    private final ProjectDao projectDao;

    public ProjectServiceImpl(ProjectValidator projectValidator, ProjectDao projectDao) {
        this.projectValidator = projectValidator;
        this.projectDao = projectDao;
    }

    @Override
    public Project saveProject(@Valid CreateProjectTO createProjectTO) {
        Project project = createProjectTO.convertToEntity();
        return projectDao.save(project);
    }

    @Transactional
    @Override
    public void editProject(@Valid EditProjectTO editProjectTO) {
        Project project = projectValidator.validateForUpdate(editProjectTO);
        project.setName(editProjectTO.getName());
        projectDao.update(project);
    }
}
