package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;
import com.xitaymin.tasktracker.service.GenericService;
import com.xitaymin.tasktracker.service.ProjectService;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.xitaymin.tasktracker.service.impl.TeamServiceImpl.TEAM_NOT_FOUND;
import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

@Service
public class ProjectServiceImpl extends GenericService implements ProjectService {

    public static final String PRODUCT_OWNER_NOT_VALID =
            "User with id = %d doesn't have role MANAGER, and can't be assign as product owner.";
    public static final String PROJECT_NOT_FOUND = "Project with id = %d doesn't exist.";
    private final ProjectDao projectDao;
    private final TeamDao teamDao;
    private final UserDAO userDao;

    public ProjectServiceImpl(ProjectDao projectDao, TeamDao teamDao, UserDAO userDao) {
        this.projectDao = projectDao;
        this.teamDao = teamDao;
        this.userDao = userDao;
    }

    @Override
    public Project saveProject(CreateProjectTO createProjectTO) {
        Project project = createProjectTO.convertToEntity();

        return projectDao.save(project);
    }

    @Transactional
    @Override
    public void editProject(EditProjectTO editProjectTO) {
        long projectId = editProjectTO.getId();

        Project project = projectDao.findById(projectId);
        throwExceptionIfAbsent(PROJECT_NOT_FOUND, project, projectId);

        project.setName(editProjectTO.getName());
    }

    @Transactional
    @Override
    public void addTeam(long projectId, long teamId) {
        Project project = projectDao.findByIdWithTeams(projectId);
        throwExceptionIfAbsent(PROJECT_NOT_FOUND, project, projectId);

        Team team = teamDao.findById(teamId);
        throwExceptionIfAbsent(TEAM_NOT_FOUND, team, teamId);

        project.addTeam(team);

    }

    @Transactional
    @Override
    public void assignProductOwner(long projectId, long productOwnerId) {
        Project project = projectDao.findById(projectId);
        throwExceptionIfAbsent(PROJECT_NOT_FOUND, project, projectId);

        User user = userDao.findOne(productOwnerId);
        throwExceptionIfAbsent(USER_NOT_FOUND, user, productOwnerId);

        if (user.getRoles().contains(Role.MANAGER)) {
            project.setProductOwner(user);
        } else {
            throw new InvalidRequestParameterException(String.format(PRODUCT_OWNER_NOT_VALID, productOwnerId));
        }
    }

    @Transactional
    @Override
    public void deleteTeam(long projectId, long teamId) {
        Project project = projectDao.findByIdWithTeams(projectId);
        throwExceptionIfAbsent(PROJECT_NOT_FOUND, project, projectId);

        Team team = teamDao.findById(teamId);
        throwExceptionIfAbsent(TEAM_NOT_FOUND, team, teamId);

        project.removeTeam(team);
    }

}
