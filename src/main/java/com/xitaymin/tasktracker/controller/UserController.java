package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.dto.user.EditUserTO;
import com.xitaymin.tasktracker.dto.user.FullUserTO;
import com.xitaymin.tasktracker.dto.user.UserRoleTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;
import com.xitaymin.tasktracker.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

import static com.xitaymin.tasktracker.controller.UserController.USERS;

@RestController
@RequestMapping(USERS)
public class UserController {
    public static final String USERS = "/users";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public UserViewTO createUser(@Valid @RequestBody CreateUserTO createUserTO) {
        return userService.save(createUserTO);
    }

    @PutMapping()
    public void editUser(@Valid @RequestBody EditUserTO editUserTO) {
        userService.editUser(editUserTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping()
    public Collection<UserViewTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/role")
    public void addRole(@Valid @RequestBody UserRoleTO roleTO) {
        userService.addRole(roleTO);
    }

    @DeleteMapping("/role")
    public void removeRole(@Valid @RequestBody UserRoleTO roleTO) {
        userService.deleteRole(roleTO);
    }


    @PutMapping("/{user}/task/{task}")
    public void assignTask(@PathVariable long task, @PathVariable long user) {
        userService.assignTask(user, task);
    }

    @GetMapping("/{id}")
    public FullUserTO getUserWithTasksById(@PathVariable long id) {
        return userService.getById(id);
    }

}






