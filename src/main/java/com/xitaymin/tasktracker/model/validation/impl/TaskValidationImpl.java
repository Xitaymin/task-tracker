package com.xitaymin.tasktracker.model.validation.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.exception.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.exception.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validation.TaskValidation;
import com.xitaymin.tasktracker.model.validation.UserValidation;
import org.springframework.stereotype.Service;

@Service
public class TaskValidationImpl implements TaskValidation {
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final UserValidation userValidation;

    public TaskValidationImpl(TaskDAO taskDAO, UserDAO userDAO, UserValidation userValidation) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.userValidation = userValidation;
    }

    @Override
    public boolean isTaskValidForUpdate(Task task) {
        Task oldTask = taskDAO.findOne(task.getId());
        if (oldTask != null) {
            if (task.getReporter() != oldTask.getReporter()) {
                throw new InvalidRequestParameterException(String.format("Reporter shouldn't be changed. Old value = %s  ", oldTask.getReporter()));
            }
            if (task.getAssignee() != oldTask.getAssignee()) {
                throw new InvalidRequestParameterException(String.format("Assignee shouldn't be changed in this request. Old value = %s  ",
                                                                         oldTask.getAssignee()));
            }
            if (isAbsent(task.getTitle())) {
                throw new InvalidRequestParameterException("Title is required and shouldn't be empty. ");
            }
            if (isAbsent(task.getDescription())) {
                throw new InvalidRequestParameterException("Description is required and shouldn't be empty. ");
            }
            return true;
        } else {
            throw new NotFoundResourceException(String.format("Task with id = %s doesn't exist ", task.getId()));
        }
    }

    @Override
    public boolean isTaskValidForSave(Task task) {
        long assigneeId = task.getAssignee();
        long reporterId = task.getReporter();

        if (userValidation.isUnavailable(userDAO.findOne(reporterId))) {
            throw new NotFoundResourceException(String.format("Not found reporter with id = %s. ", reporterId));
        }
        if (userValidation.isUnavailable(userDAO.findOne(assigneeId))) {
            throw new NotFoundResourceException(String.format("Not found assignee with id = %s. ", assigneeId));
        }
        if (isAbsent(task.getTitle())) {
            throw new InvalidRequestParameterException("Title is required and shouldn't be empty. ");
        }
        if (isAbsent(task.getDescription())) {
            throw new InvalidRequestParameterException("Description is required and shouldn't be empty. ");
        }
        return true;
    }

    private boolean isAbsent(String text) {
        return (text == null || text.isEmpty());
    }

}
