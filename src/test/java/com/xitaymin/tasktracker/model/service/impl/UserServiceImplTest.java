package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.validation.UserValidation;
import com.xitaymin.tasktracker.model.validation.impl.UserValidationImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    UserDAO userDAO = mock(UserDAOImpl.class);
    UserValidation userValidation = mock(UserValidationImpl.class);
    User user = mock(User.class);
    UserServiceImpl userService = new UserServiceImpl(userDAO, userValidation);

    @Test
    public void ifValidUserUpdates() {
        when(userValidation.isUserValidForUpdate(user)).thenReturn(true);
        userService.editUser(user);
        verify(userDAO).update(user);
    }
}