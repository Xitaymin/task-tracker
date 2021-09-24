package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.service.TaskService;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.TaskValidator;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskDAO taskDAO;
    private final TaskValidator taskValidator;

    public TaskServiceImpl(TaskDAO taskDAO, TaskValidator taskValidator) {
        this.taskDAO = taskDAO;
        this.taskValidator = taskValidator;
    }

    @Override
    public void editTask(Task task) {
        taskValidator.validateForUpdate(task);
        taskDAO.update(task);
    }

    @Override
    public Task getTask(long id) {
        Task task = taskDAO.findOne(id);
        if (task == null) {
            throw new NotFoundResourceException(String.format("Task with id = %s doesn't exist. ", id));
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
        taskValidator.validateForSave(task);
        return taskDAO.save(task);
    }
}