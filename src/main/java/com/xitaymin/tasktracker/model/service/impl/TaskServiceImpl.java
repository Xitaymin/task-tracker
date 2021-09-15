package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    public static final Logger LOGGER =
            LoggerFactory.getLogger(TaskServiceImpl.class);
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public TaskServiceImpl(UserDAO userDAO, TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    @Override
    public boolean saveTask(Task task) {
        LOGGER.debug("Deserialized task " + task.toString());
        StringBuilder messageBuilder = new StringBuilder();
        String message;
        Long assigneeId = null;
        Long reporterId = null;
        User reporter;
        User assignee;
        String description = task.getDescription();
        String title = task.getTitle();

        try {
            reporterId = Long.parseLong(task.getReporter());
        } catch (NumberFormatException e) {
            messageBuilder.append("Reporter id is required.\n");
        }
        if (reporterId != null) {
            reporter = userDAO.findOne(reporterId);
            if (reporter == null || reporter.isDeleted()) {
                messageBuilder.append("Reporter with such id doesn't exist. " + "\n");
            }
        }
        try {
            assigneeId = Long.parseLong(task.getAssignee());
        } catch (NumberFormatException e) {
        }
        if (assigneeId != null) {
            assignee = userDAO.findOne(assigneeId);
            if (assignee == null || assignee.isDeleted()) {
                messageBuilder.append("Assignee with such id doesn't exist.\n");
            }
        }
        if (title == null || title.isEmpty()) {
            messageBuilder.append("Title is required and shouldn't be empty. "
                                          + "\n");
        }
        if (description == null || description.isEmpty()) {
            messageBuilder.append("Description is required and shouldn't be " + "empty. \n");
        }
        if (reporterId != null && userDAO.findOne(reporterId).isDeleted()) {
            messageBuilder.append("Not found reporter with such id.\n");
        }
        message = messageBuilder.toString();
        if (message.isEmpty()) {
            taskDAO.save(task);
            return true;
        } else {
            throw new IllegalArgumentException(message);
        }
    }
}
