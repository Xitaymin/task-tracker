package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public TaskServiceImpl(UserDAO userDAO, TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    @Override
    public Task assignTask(Long userId, Long taskId) {
        StringBuilder messageBuilder = new StringBuilder();
        Task task = taskDAO.findOne(taskId);
        if (task == null) {
            messageBuilder.append(String.format("Not found task with id = %s\n", taskId));
        }
        User user = userDAO.findOne(userId);
        if (user == null || user.isDeleted()) {
            messageBuilder.append(String.format("Not found user with id = %s\n", userId));
        }
        String message = messageBuilder.toString();
        if (message.isEmpty()) {
            task.setAssignee(userId);
            return task;
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public Task editTask(Task task) {
        StringBuilder messageBuilder = new StringBuilder();
        Task oldTask = taskDAO.findOne(task.getId());
        String description = task.getDescription();
        String title = task.getTitle();
        if (oldTask != null) {
            if (isReporterChanged(task.getReporter(), oldTask.getReporter())) {
                messageBuilder.append(String.format("Reporter shouldn't be changed. Old value = %s \n", oldTask.getReporter()));
            }
            if (isAssigneeChanged(task.getAssignee(), oldTask.getAssignee())) {
                messageBuilder.append(String.format("Assignee shouldn't be changed in this request. Use PUT tracker/task/{taskId}/user/{userId}. Old value = " +
                                                            "%s\n",
                                                    oldTask.getAssignee()));
            }
            if (title == null || title.isEmpty()) {
                messageBuilder.append("Title is required and shouldn't be empty.\n");
            }
            if (description == null || description.isEmpty()) {
                messageBuilder.append("Description is required and shouldn't be empty.\n");
            }
        } else {
            messageBuilder.append(String.format("Task with id = %s doesn't exist\n", task.getId()));
        }
        String message = messageBuilder.toString();

        if (message.isEmpty()) {
            taskDAO.update(task);
            return task;
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    private boolean isAssigneeChanged(Long assignee, Long oldAssignee) {
        if (assignee != null && oldAssignee != null) {
            return !assignee.equals(oldAssignee);
        } else {
            return !(assignee == null && oldAssignee == null);
        }
    }

    private boolean isReporterChanged(Long reporter, Long oldReporter) {
        if (reporter == null) {
            return true;
        } else {
            return !reporter.equals(oldReporter);
        }
    }

    @Override
    public Task getTask(Long id) {
        Task task = taskDAO.findOne(id);
        if (task == null) {
            throw new IllegalArgumentException(String.format("Task with id = %s doesn't exist.\n", id));
        } else {
            return task;
        }
    }

    @Override
    public Collection<Task> getTasks() {
        return taskDAO.findAll();
    }

    @Override
    public Task saveTask(Task task) {
        StringBuilder messageBuilder = new StringBuilder();
        Long assigneeId = task.getAssignee();
        Long reporterId = task.getReporter();
        String description = task.getDescription();
        String title = task.getTitle();

        if (reporterId == null) {
            messageBuilder.append("Reporter id is required.\n");
        } else {
            User reporter = userDAO.findOne(reporterId);
            if (reporter == null || reporter.isDeleted()) {
                messageBuilder.append(String.format("Not found reporter with id = %s.\n", reporterId));
            }
        }
        if (assigneeId != null) {
            User assignee = userDAO.findOne(assigneeId);
            if (assignee == null || assignee.isDeleted()) {
                messageBuilder.append(String.format("Not found assignee with id = %s.\n", assigneeId));
            }
        }
        if (title == null || title.isEmpty()) {
            messageBuilder.append("Title is required and shouldn't be empty.\n");
        }
        if (description == null || description.isEmpty()) {
            messageBuilder.append("Description is required and shouldn't be empty.\n");
        }
        String message = messageBuilder.toString();
        if (message.isEmpty()) {
            return taskDAO.save(task);
        } else {
            throw new IllegalArgumentException(message);
        }
    }
}
