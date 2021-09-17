package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserTasks;
import com.xitaymin.tasktracker.model.service.TaskService;
import com.xitaymin.tasktracker.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("tracker")
public class Controller {

    public static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final UserService userService;
    private final TaskService taskService;

    public Controller(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user) {
        LOGGER.debug("Create user method is starting.");
        return userService.save(user);
    }

    @PutMapping("/user/edit")
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
    }

    @GetMapping("/user/{id}")
    public UserTasks getUserWithTasksById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/task/create")
    public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @GetMapping("/task/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @GetMapping("/tasks")
    public Collection<Task> getTasks() {
        return taskService.getTasks();
    }

    @PutMapping("task/assign")
    //todo replace on PathVariable
    public Task assignTask(@RequestParam Long user, @RequestParam Long task) {
        LOGGER.debug("Inside assign task method in controller");
        return taskService.assignTask(user, task);
    }

    @PutMapping("task/edit")
    public Task editTask(@RequestBody Task task) {
        return taskService.editTask(task);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        LOGGER.debug("Inside exception handler.");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        LOGGER.debug("Inside exception handler.");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}






