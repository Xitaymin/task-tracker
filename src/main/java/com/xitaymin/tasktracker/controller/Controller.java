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
public class Controller {

    public static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final UserService userService;
    private final TaskService taskService;

    public Controller(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        LOGGER.debug("Create user method is starting.");
        return userService.save(user);
    }

    @PutMapping("/edit")
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }

    @DeleteMapping("/delete/{id}")
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

    //    @PostMapping("/task/create")
    //    public Task createTask(@RequestBody Task task) {
    //        return taskService.saveTask(task);
    //    }
    //
    //    @GetMapping("/task/{id}")
    //    public Task getTask(@PathVariable Long id) {
    //        return taskService.getTask(id);
    //    }
    //
    //    @GetMapping("/tasks")
    //    public Collection<Task> getTasks() {
    //        return taskService.getTasks();
    //    }
    //
    //    @PutMapping("task/assign")
    //    //todo replace on PathVariable
    //    public Task assignTask(@RequestParam Long user, @RequestParam Long task) {
    //        LOGGER.debug("Inside assign task method in controller");
    //        return taskService.assignTask(user, task);
    //    }
    //
    //    @PutMapping("task/edit")
    //    public Task editTask(@RequestBody Task task) {
    //        return taskService.editTask(task);
    //    }
}






