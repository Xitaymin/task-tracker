package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserWithTasks;

import java.util.List;

public interface UserService {
    void save(User user);

    boolean editUser(User user);

    boolean deleteUser(User user);


    UserWithTasks getById(Long id);

    List<User> getAllUsers();
}
