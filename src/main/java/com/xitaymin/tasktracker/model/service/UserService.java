package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.CreateUserTO;
import com.xitaymin.tasktracker.model.dto.EditUserTO;

import java.util.Collection;

public interface UserService {
    User save(CreateUserTO user);

    void editUser(EditUserTO user);

    void deleteUser(long id);

    Collection<User> getAllUsers();
}
