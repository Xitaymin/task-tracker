package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.UserValidator;
import com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.util.stream.Stream;

import static com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl.EMAIL_IN_USE;
import static com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl.EMAIL_REQUIRED;
import static com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    private final UserDAO userDAO = mock(UserDAOImpl.class);
    private final UserValidator userValidator = new UserValidatorImpl(userDAO);
    private final UserServiceImpl userService = new UserServiceImpl(userDAO, userValidator);

    private static Stream<Arguments> provideUserWithInvalidEmails() {
        return Stream.of(
                Arguments.of(new User(100, "Name", null, false)),
                Arguments.of(new User(100, "Name", "", false)),
                Arguments.of(new User(100, "Name", " ", false)));
    }

    @AfterEach
    void tearDown() {
        reset(userDAO);
    }

    @Test
    void ifValidUserPassToSave() {
        User user = new User(0, "Name", "user_email@gmail.com", false);
        when(userDAO.findByEmail(anyString())).thenReturn(null);
        userService.save(user);
        ArgumentCaptor<User> taskCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDAO).save(taskCaptor.capture());
        assertThat(taskCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new User(0, "Name", "user_email@gmail.com", false));
    }

    @Test
    void ifUserWithExistingEmailFailToSave() {
        User user = new User(0, "Name", "user_email@gmail.com", false);
        when(userDAO.findByEmail(anyString())).thenReturn(mock(User.class));
        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> userService.save(user));
        assertThat(throwable).hasMessage(String.format(EMAIL_IN_USE, user.getEmail()));
        verify(userDAO, never()).save(user);
    }

    @Test
    void ifValidUserPassToUpdate() {
        User user = new User(1, "Name", "user_email@gmail.com", false);
        when(userDAO.findOne(1)).thenReturn(new User(1, "Old name", "another_user_email@gmail.com", false));
        when(userDAO.findByEmail("user_email@gmail.com")).thenReturn(null);
        userService.editUser(user);
        ArgumentCaptor<User> taskCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDAO).update(taskCaptor.capture());
        assertThat(taskCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new User(1, "Name", "user_email@gmail.com", false));
    }

    @Test
    void ifUserWithAbsentIdFailToUpdate() {
        User user = new User(100, "Name", "user_email@gmail.com", false);
        when(userDAO.findOne(user.getId())).thenReturn(null);
        Throwable throwable = assertThrows(NotFoundResourceException.class, () -> userService.editUser(user));
        assertThat(throwable).hasMessage(String.format(USER_NOT_FOUND, user.getId()));
        verify(userDAO, never()).update(user);
    }

    @ParameterizedTest()
    @MethodSource("provideUserWithInvalidEmails")
    void ifUserWithoutEmailFailToUpdate(User user) {
        when(userDAO.findOne(user.getId())).thenReturn(new User(user.getId(), "Old name", "user_email@gmail.com", false));
        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> userService.editUser(user));
        assertThat(throwable).hasMessage(EMAIL_REQUIRED);
        verify(userDAO, never()).update(user);
    }


}

