package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.model.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.model.dto.user.EditUserTO;
import com.xitaymin.tasktracker.model.dto.user.UserViewTO;

import java.util.Collection;

public interface UserService {
    UserViewTO save(CreateUserTO user);

    void editUser(EditUserTO user);

    void deleteUser(long id);

    Collection<UserViewTO> getAllUsers();
}
