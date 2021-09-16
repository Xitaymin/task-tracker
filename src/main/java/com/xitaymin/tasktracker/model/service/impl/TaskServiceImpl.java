package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public boolean assignTask(Long userId, Long taskId) {
        LOGGER.debug("Task id = {}, User id = {}", taskId, userId);
        StringBuilder messageBuilder = new StringBuilder();
        Task task = taskDAO.findOne(taskId);
        if (task == null) {
            messageBuilder.append(String.format("Not found task with id = %s"
                    , taskId));
        }
        User user = userDAO.findOne(userId);
        if (user == null || user.isDeleted()) {
            messageBuilder.append(String.format("Not found user with id = %s"
                    , userId));
        }
        String message = messageBuilder.toString();
        if (message.isEmpty()) {
            task.setAssignee(userId.toString());
            LOGGER.debug(task.toString());
            return true;
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public Task getTask(Long id) {
        Task task = taskDAO.findOne(id);
        if (task == null) {
            throw new IllegalArgumentException(String.format("Task with id = "
                                                                     + "%s " +
                                                                     "doesn't" +
                                                                     " exist" +
                                                                     ".", id));
        } else {
            return task;
        }
    }

    @Override
    public Collection<Task> getTasks() {
        return taskDAO.findAll();
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
                messageBuilder.append(String.format("Not found reporter with " +
                                                            "id = %s.\n",
                                                    reporterId));
            }
        }
        try {
            assigneeId = Long.parseLong(task.getAssignee());
        } catch (NumberFormatException e) {
            task.setAssignee(null);
        }
        if (assigneeId != null) {
            assignee = userDAO.findOne(assigneeId);
            if (assignee == null || assignee.isDeleted()) {
                messageBuilder.append(String.format("Not found assignee with " +
                                                            "id = %s.\n",
                                                    assigneeId));
            }
        }
        if (title == null || title.isEmpty()) {
            messageBuilder.append("Title is required and shouldn't be empty" +
                                          ".\n");
        }
        if (description == null || description.isEmpty()) {
            messageBuilder.append("Description is required and shouldn't be " +
                                          "empty.\n");
        }
        //        if (reporterId != null && userDAO.findOne(reporterId)
        //        .isDeleted()) {
        //            messageBuilder.append(String.format("Not found reporter
        //            with id = %s.\n",reporterId));
        //        }
        message = messageBuilder.toString();
        if (message.isEmpty()) {
            taskDAO.save(task);
            return true;
        } else {
            throw new IllegalArgumentException(message);
        }
    }
}
