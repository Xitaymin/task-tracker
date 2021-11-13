package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.PersistentObject;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.user.UserWithTasksAndTeamsTO;
import com.xitaymin.tasktracker.model.service.UserWithTasksService;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.validators.UserValidator;
import com.xitaymin.tasktracker.model.service.validators.UserWithTasksValidator;
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
        if (userWithTasksValidator.areUserAndTaskValidToAssign(userId, taskId)) {
            Task task = taskDAO.findOne(taskId);
//            task.setAssignee(userId);
            taskDAO.update(task);
        }
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
