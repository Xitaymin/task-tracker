package com.xitaymin.tasktracker.model.service.validators.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.validators.UserValidator;
import com.xitaymin.tasktracker.model.service.validators.UserWithTasksValidator;
import org.springframework.stereotype.Service;

import static com.xitaymin.tasktracker.model.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;
import static com.xitaymin.tasktracker.model.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

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
            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, taskId));
        }
        if (userValidator.isUnavailable(userDAO.findOne(userId))) {
            throw new NotFoundResourceException(String.format(USER_NOT_FOUND, userId));
        }
        return true;
    }
}
