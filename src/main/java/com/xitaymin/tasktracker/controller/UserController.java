package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserTasks;
import com.xitaymin.tasktracker.model.service.TaskService;
import com.xitaymin.tasktracker.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("tracker/user")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        LOGGER.debug("Create user method is starting.");
        return userService.save(user);
    }

    @PutMapping()
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserTasks getUserWithTasksById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping()
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

}






