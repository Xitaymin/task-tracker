package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.model.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(TaskController.TASKS)
public class TaskController {

    private final TaskService taskService;
    public static final String TASKS = "/tasks";

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public Task createTask(@Valid @RequestBody CreateTaskTO taskTO) {
        return taskService.saveTask(taskTO);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }

    @GetMapping()
    public Collection<Task> getTasks() {
        return taskService.getTasks();
    }

    @PutMapping()
    public void editTask(@Valid @RequestBody Task task) {
        taskService.editTask(task);
    }
}
