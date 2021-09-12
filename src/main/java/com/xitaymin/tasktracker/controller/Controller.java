package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserWithTasks;
import com.xitaymin.tasktracker.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("tracker")
public class Controller {

    public static final Logger LOGGER =
            LoggerFactory.getLogger(Controller.class);
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public void createUser(@RequestBody User user) {
        LOGGER.debug("Create user method is starting.");
        try {
            userService.save(user);
        } catch (Exception e) {
            //todo process exception illegal argument email
            e.printStackTrace();


        }
    }

    @PostMapping("/user/edit")
    public void editUser(@RequestBody User user) {
        try {
            userService.editUser(user);
        } catch (Exception e) {
            //todo process exception illegal argument email or id
            e.printStackTrace();
        }

    }

    @DeleteMapping("/user/delete")
    public void deleteUser(@RequestBody User user) {
        try {
            userService.deleteUser(user);
        } catch (Exception e) {
            //todo no such user
            e.printStackTrace();
        }
    }

    @GetMapping("/user/{id}")
    public void getUserWithTasksById(@PathParam("id") Long id) {
        try {
            UserWithTasks userWithTasks = userService.getById(id);
        } catch (Exception e) {
            //todo wrong id
            e.printStackTrace();
        }
    }

    @GetMapping("/users")
    public void getallUsers() {
        List<User> users = userService.getAllUsers();
    }
}






