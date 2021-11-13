package com.xitaymin.tasktracker.service.validators.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.UserValidator;
import com.xitaymin.tasktracker.service.validators.UserWithTasksValidator;
import org.springframework.stereotype.Service;

import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.ASSIGNEE_NOT_FOUND;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.ASSIGNEE_NOT_IN_TEAM;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;
import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

@Service
public class UserWithTasksValidatorImpl implements UserWithTasksValidator {
    private final UserValidator userValidator;
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public UserWithTasksValidatorImpl(UserValidator userValidator, UserDAO userDAO, TaskDAO taskDAO) {
        this.userValidator = userValidator;
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    @Override
    public boolean isAssigneeValid(long userId, long taskId) {

        if (taskDAO.findOne(taskId) == null) {
            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, taskId));
        }
        if (userValidator.isUnavailable(userDAO.findOne(userId))) {
            throw new NotFoundResourceException(String.format(USER_NOT_FOUND, userId));
        }
        return true;
    }

    @Override
    public void validateToAssign(User assignee, Task task) {
        if (userValidator.isUnavailable(assignee)) {
            throw new NotFoundResourceException(String.format(ASSIGNEE_NOT_FOUND, assignee.getId()));
        }
        Project project = task.getProject();
        boolean isAssigneeInTeam = project.getTeams().contains(assignee.getTeam());
        if (!isAssigneeInTeam) {
            throw new InvalidRequestParameterException(String.format(ASSIGNEE_NOT_IN_TEAM,
                    assignee.getId(),
                    project.getId()));
        }
    }
}
