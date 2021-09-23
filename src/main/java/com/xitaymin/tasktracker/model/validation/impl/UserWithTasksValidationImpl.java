package com.xitaymin.tasktracker.model.validation.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validation.UserValidation;
import com.xitaymin.tasktracker.model.validation.UserWithTasksValidation;
import org.springframework.stereotype.Service;

@Service
public class UserWithTasksValidationImpl implements UserWithTasksValidation {
    private final UserValidation userValidation;
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public UserWithTasksValidationImpl(UserValidation userValidation, UserDAO userDAO, TaskDAO taskDAO) {
        this.userValidation = userValidation;
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    @Override
    public boolean areUserAndTaskValidToAssign(long userId, long taskId) {

        if (taskDAO.findOne(taskId) == null) {
            throw new NotFoundResourceException(String.format("Not found task with id = %s ", taskId));
        }
        if (userValidation.isUnavailable(userDAO.findOne(userId))) {
            throw new NotFoundResourceException(String.format("Not found user with id = %s ", userId));
        }
        return true;
    }
}
