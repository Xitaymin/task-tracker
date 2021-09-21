package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.model.dto.UserWithTasks;
import com.xitaymin.tasktracker.model.service.UserWithTasksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWithTasksController {

    private final UserWithTasksService userWithTasksService;

    public UserWithTasksController(UserWithTasksService userWithTasksService) {
        this.userWithTasksService = userWithTasksService;
    }

    @PutMapping("users/{user}/task/{task}")
    public void assignTask(@PathVariable long task, @PathVariable long user) {
        userWithTasksService.assignTask(user, task);
    }

    @GetMapping("users/{id}")
    public UserWithTasks getUserWithTasksById(@PathVariable long id) {
        return userWithTasksService.getById(id);
    }
}
