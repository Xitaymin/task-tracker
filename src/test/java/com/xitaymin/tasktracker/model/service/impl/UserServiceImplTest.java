package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.validators.UserValidator;
import com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl.EMAIL_IN_USE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    UserDAO userDAO = mock(UserDAOImpl.class);
    UserValidator userValidator = new UserValidatorImpl(userDAO);
    UserServiceImpl userService = new UserServiceImpl(userDAO, userValidator);

    @Test
    void ifValidUserPassToSave() {
        User user = new User(0, "Name", "email", false);
        when(userDAO.findByEmail(anyString())).thenReturn(null);
        userService.save(user);
        ArgumentCaptor<User> taskCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDAO).save(taskCaptor.capture());
        assertThat(taskCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new User(0, "Name", "email", false));
    }

    @Test
    void ifUserWithExistingEmailFailToSave() {
        User user = new User(0, "Name", "email", false);
        when(userDAO.findByEmail(anyString())).thenReturn(mock(User.class));
        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> userService.save(user));
        assertThat(throwable).hasMessage(String.format(EMAIL_IN_USE, user.getEmail()));
        verify(userDAO, never()).save(user);
    }

}