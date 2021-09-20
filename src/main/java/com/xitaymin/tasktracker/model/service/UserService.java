package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserWithTasks;

import java.util.Collection;

public interface UserService {
    User save(User user);

    void editUser(User user);

    void deleteUser(long id);

    UserWithTasks getById(long id);

    Collection<User> getAllUsers();
}
