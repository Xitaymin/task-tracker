package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.model.dto.user.UserWithTasksAndTeamsTO;
import com.xitaymin.tasktracker.model.service.UserWithTasksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWithTasksAndTeamsController {

    private final UserWithTasksService userWithTasksService;

    public UserWithTasksAndTeamsController(UserWithTasksService userWithTasksService) {
        this.userWithTasksService = userWithTasksService;
    }

    @PutMapping("users/{user}/task/{task}")
    public void assignTask(@PathVariable long task, @PathVariable long user) {
        userWithTasksService.assignTask(user, task);
    }

    @GetMapping("users/{id}")
    public UserWithTasksAndTeamsTO getUserWithTasksById(@PathVariable long id) {
        return userWithTasksService.getById(id);
    }
}
