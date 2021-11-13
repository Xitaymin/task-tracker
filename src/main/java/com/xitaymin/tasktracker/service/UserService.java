package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.dto.user.EditUserTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;

import java.util.Collection;

public interface UserService {
    UserViewTO save(CreateUserTO user);

    void editUser(EditUserTO user);

    void deleteUser(long id);

    Collection<UserViewTO> getAllUsers();
}
