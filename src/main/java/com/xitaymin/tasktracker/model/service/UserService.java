package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserWithTasks;

import java.util.Collection;

public interface UserService {
    void save(User user);

    void editUser(User user);

    void deleteUser(Long id);


    UserWithTasks getById(Long id);

    Collection<User> getAllUsers();
}
