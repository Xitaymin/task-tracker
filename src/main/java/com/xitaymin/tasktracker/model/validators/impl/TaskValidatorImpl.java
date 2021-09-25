package com.xitaymin.tasktracker.model.validators.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.TaskValidator;
import org.springframework.stereotype.Service;

@Service
public class TaskValidatorImpl implements TaskValidator {
    public static final String REQUIRED_TITLE = "Title is required and shouldn't be empty.";
    public static final String TASK_NOT_FOUND = "Task with id = %s doesn't exist.";
    public static final String REPORTER_SHOULDNT_CHANGE = "Reporter shouldn't be changed. Old value = %s.";
    public static final String ASSIGNEE_SHOULDNT_CHANGE = "Assignee shouldn't be changed in this request. Old value = %s.";
    public static final String REQUIRED_DESCRIPTION = "Description is required and shouldn't be empty.";
    public static final String REPORTER_NOT_FOUND = "Not found reporter with id = %s.";
    public static final String ASSIGNEE_NOT_FOUND = "Not found assignee with id = %s.";
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;

    public TaskValidatorImpl(TaskDAO taskDAO, UserDAO userDAO) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void validateForUpdate(Task task) {
        Task oldTask = taskDAO.findOne(task.getId());
        if (oldTask != null) {
            if (task.getReporter() != oldTask.getReporter()) {
                throw new InvalidRequestParameterException(String.format(REPORTER_SHOULDNT_CHANGE, oldTask.getReporter()));
            }
            if (!isAssigneeValidForUpdate(task.getAssignee(), oldTask.getAssignee())) {
                throw new InvalidRequestParameterException(String.format(ASSIGNEE_SHOULDNT_CHANGE, oldTask.getAssignee()));
            }

            if (isTextFieldAbsent(task.getTitle())) {
                throw new InvalidRequestParameterException(REQUIRED_TITLE);
            }
            if (isTextFieldAbsent(task.getDescription())) {
                throw new InvalidRequestParameterException(REQUIRED_DESCRIPTION);
            }
        } else {
            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, task.getId()));
        }
    }

    @Override
    public void validateForSave(Task task) {
        Long assigneeId = task.getAssignee();
        long reporterId = task.getReporter();

        if (isUserUnavailable(userDAO.findOne(reporterId))) {
            throw new NotFoundResourceException(String.format(REPORTER_NOT_FOUND, reporterId));
        }
        if (assigneeId != null) {
            if (isUserUnavailable(userDAO.findOne(assigneeId))) {
                throw new NotFoundResourceException(String.format(ASSIGNEE_NOT_FOUND, assigneeId));
            }
        }
        if (isTextFieldAbsent(task.getTitle())) {
            throw new InvalidRequestParameterException(REQUIRED_TITLE);
        }
        if (isTextFieldAbsent(task.getDescription())) {
            throw new InvalidRequestParameterException(REQUIRED_DESCRIPTION);
        }
    }

    private boolean isTextFieldAbsent(String text) {
        return (text == null || text.isBlank());
    }

    private boolean isUserUnavailable(User user) {
        return (user == null || user.isDeleted());
    }


    private boolean isAssigneeValidForUpdate(Long assignee, Long oldAssignee) {
        if (oldAssignee == null && assignee == null) {
            return true;
        } else if ((oldAssignee == null) ^ (assignee == null)) {
            return false;
        } else {
            return assignee.equals(oldAssignee);
        }
    }

}

