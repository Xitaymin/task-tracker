package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.PersistentObject;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.user.UserWithTasksAndTeamsTO;
import com.xitaymin.tasktracker.service.UserWithTasksService;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.UserValidator;
import com.xitaymin.tasktracker.service.validators.UserWithTasksValidator;
import com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserWithTasksServiceImpl implements UserWithTasksService {
    private final UserWithTasksValidator userWithTasksValidator;
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final UserValidator userValidator;

    public UserWithTasksServiceImpl(UserWithTasksValidator userWithTasksValidator, TaskDAO taskDAO, UserDAO userDAO,
                                    UserValidator userValidator) {
        this.userWithTasksValidator = userWithTasksValidator;
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.userValidator = userValidator;
    }

    @Override
    public void assignTask(long userId, long taskId) {
        Task task = taskDAO.findFullTask(taskId);
        if (task == null) {
            throw new NotFoundResourceException(String.format(TaskValidatorImpl.TASK_NOT_FOUND, taskId));
        }
        User assignee = userDAO.findOne(taskId);
        userWithTasksValidator.validateToAssign(assignee, task);
        task.setAssignee(assignee);
        assignee.getTasks().add(task);
        userDAO.update(assignee);
    }


    @Override
    public UserWithTasksAndTeamsTO getById(long id) {
        UserWithTasksAndTeamsTO userTO;
        User user = userDAO.findByIdWithTasksAndTeams(id);
        if (userValidator.isUnavailable(user)) {
            throw new InvalidRequestParameterException(String.format("User with id = %s not found", id));
        } else {
            userTO = toTO(user);
        }
        return userTO;
    }

    private UserWithTasksAndTeamsTO toTO(User user) {
        Set<Long> tasksId = user.getTasks().stream().map(PersistentObject::getId).collect(Collectors.toSet());
        return new UserWithTasksAndTeamsTO(user.getId(),
                user.getName(),
                user.getEmail(),
                user.isDeleted(),
                user.getRoles(),
                tasksId,
                user.getTeam().getId());
    }
}
