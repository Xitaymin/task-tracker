package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;
import com.xitaymin.tasktracker.service.ProjectService;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.ProjectValidator;
import com.xitaymin.tasktracker.service.validators.impl.ProjectValidatorImpl;
import com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;


//Добавить/удалить команду в проект.
//Назначить productOwner, он должен иметь роль MANAGER

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectValidator projectValidator;
    private final ProjectDao projectDao;
    private final TeamDao teamDao;

    public ProjectServiceImpl(ProjectValidator projectValidator, ProjectDao projectDao, TeamDao teamDao) {
        this.projectValidator = projectValidator;
        this.projectDao = projectDao;
        this.teamDao = teamDao;
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

    @Transactional
    @Override
    public void addTeam(long projectId, long teamId) {
        Project project = projectDao.findByIdWithTeams(projectId);
        if (project == null) {
            throw new NotFoundResourceException(String.format(ProjectValidatorImpl.PROJECT_NOT_FOUND, projectId));
        }


        Team team = teamDao.findById(teamId);
        if (team == null) {
            throw new NotFoundResourceException(String.format(TeamValidatorImpl.TEAM_NOT_FOUND, teamId));
        }

        team.getProjects().add(project);
        project.getTeams().add(team);
        projectDao.update(project);
    }
}
