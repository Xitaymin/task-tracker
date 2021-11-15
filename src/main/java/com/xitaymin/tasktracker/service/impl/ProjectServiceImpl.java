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
import com.xitaymin.tasktracker.service.ProjectService;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.ProjectValidator;
import com.xitaymin.tasktracker.service.validators.impl.ProjectValidatorImpl;
import com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;


//Добавить/удалить команду в проект.
//Назначить productOwner, он должен иметь роль MANAGER

@Service
public class ProjectServiceImpl implements ProjectService {

    public static final String PRODUCT_OWNER_NOT_VALID = "User with id = %d doesn't have role MANAGER, and can't be assign as product owner.";
    private final ProjectValidator projectValidator;
    private final ProjectDao projectDao;
    private final TeamDao teamDao;
    private final UserDAO userDao;

    public ProjectServiceImpl(ProjectValidator projectValidator, ProjectDao projectDao, TeamDao teamDao, UserDAO userDao) {
        this.projectValidator = projectValidator;
        this.projectDao = projectDao;
        this.teamDao = teamDao;
        this.userDao = userDao;
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

        project.addTeam(team);

//        team.getProjects().add(project);
//        project.getTeams().add(team);
//        projectDao.update(project);
    }

    @Transactional
    @Override
    public void assignProductOwner(long projectId, long productOwnerId) {
        Project project = projectDao.findById(projectId);
        if (project == null) {
            throw new NotFoundResourceException(String.format(ProjectValidatorImpl.PROJECT_NOT_FOUND, projectId));
        }
        User user = userDao.findOne(productOwnerId);
        if (user == null) {
            throw new NotFoundResourceException(String.format(USER_NOT_FOUND, productOwnerId));
        }
        if(user.getRoles().contains(Role.MANAGER)){
            project.setProductOwner(user);
        }
        else throw new InvalidRequestParameterException(String.format(PRODUCT_OWNER_NOT_VALID,productOwnerId));
    }


}
