package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.EditTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.service.TaskService;
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
    public TaskViewTO createTask(@Valid @RequestBody CreateTaskTO taskTO) {
        return taskService.saveTask(taskTO);
    }

    @GetMapping("/{id}")
    public TaskViewTO getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }

    @GetMapping()
    public Collection<TaskViewTO> getTasks() {
        return taskService.getTasks();
    }

    @PutMapping()
    public void editTask(@Valid @RequestBody EditTaskTO taskTO) {
        taskService.editTask(taskTO);
    }
}
