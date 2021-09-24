package com.xitaymin.tasktracker.model.validators.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.UserValidator;
import com.xitaymin.tasktracker.model.validators.UserWithTasksValidator;
import org.springframework.stereotype.Service;

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
    public boolean areUserAndTaskValidToAssign(long userId, long taskId) {

        if (taskDAO.findOne(taskId) == null) {
            throw new NotFoundResourceException(String.format("Not found task with id = %s ", taskId));
        }
        if (userValidator.isUnavailable(userDAO.findOne(userId))) {
            throw new NotFoundResourceException(String.format("Not found user with id = %s ", userId));
        }
        return true;
    }
}
