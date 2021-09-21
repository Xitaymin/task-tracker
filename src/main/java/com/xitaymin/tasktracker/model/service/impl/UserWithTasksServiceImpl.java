package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserWithTasks;
import com.xitaymin.tasktracker.model.exception.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.UserWithTasksService;
import com.xitaymin.tasktracker.model.validation.UserValidation;
import com.xitaymin.tasktracker.model.validation.UserWithTasksValidation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWithTasksServiceImpl implements UserWithTasksService {
    private final UserWithTasksValidation userWithTasksValidation;
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final UserValidation userValidation;

    public UserWithTasksServiceImpl(UserWithTasksValidation userWithTasksValidation, TaskDAO taskDAO, UserDAO userDAO, UserValidation userValidation) {
        this.userWithTasksValidation = userWithTasksValidation;
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.userValidation = userValidation;
    }

    @Override
    public void assignTask(long userId, long taskId) {
        if (userWithTasksValidation.areUserAndTaskValidToAssign(userId, taskId)) {
            Task task = taskDAO.findOne(taskId);
            task.setAssignee(userId);
            taskDAO.update(task);
        }
    }

    @Override
    public UserWithTasks getById(long id) {
        List<Task> tasks;
        User user = userDAO.findOne(id);
        if (userValidation.isUnavailable(user)) {
            throw new InvalidRequestParameterException(String.format("User with id = %s not found", id));
        } else {
            tasks = taskDAO.findByAssignee(id);
        }
        return new UserWithTasks(user, tasks);
    }
}
