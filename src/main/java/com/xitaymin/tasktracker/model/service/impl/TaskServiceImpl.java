package com.xitaymin.tasktracker.model.service.impl;
import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.exception.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.TaskService;
import com.xitaymin.tasktracker.model.validation.TaskValidation;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskDAO taskDAO;
    private final TaskValidation taskValidation;

    public TaskServiceImpl(TaskDAO taskDAO, TaskValidation taskValidation) {
        this.taskDAO = taskDAO;
        this.taskValidation = taskValidation;
    }

    @Override
    public void editTask(Task task) {
        if (taskValidation.isTaskValidForUpdate(task)) {
            taskDAO.update(task);
        }
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
        if (taskValidation.isTaskValidForSave(task)) {
            return taskDAO.save(task);
        }
        //todo make up something better
        else {
            return null;
        }
    }
}
