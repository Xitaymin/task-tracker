package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.model.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("tracker/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @GetMapping()
    public Collection<Task> getTasks() {
        return taskService.getTasks();
    }

    @PutMapping("/assign")
    //todo replace on PathVariable
    public Task assignTask(@RequestParam Long user, @RequestParam Long task) {
        return taskService.assignTask(user, task);
    }

    @PutMapping("/edit")
    public Task editTask(@RequestBody Task task) {
        return taskService.editTask(task);
    }
}
