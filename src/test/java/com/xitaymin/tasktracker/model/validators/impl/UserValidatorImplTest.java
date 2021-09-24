package com.xitaymin.tasktracker.model.validators.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImplTest.NOT_EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserValidatorImplTest {

    private final UserDAO userDAO = mock(UserDAOImpl.class);
    private final User user = mock(User.class);
    private final User oldUser = mock(User.class);
    public static final String NOT_NULL_EMAIL = "example@gmail.com";

    private final long id = 1;
    private final UserValidatorImpl userValidation = new UserValidatorImpl(userDAO);
    private final String anotherExistingEmail = "another@gmail.com";
    private final String absentEmail = null;
    private User validUser;

    public void setValidUser() {
        validUser = new User(1, NOT_EMPTY_STRING, NOT_NULL_EMAIL, false);
    }

    @Test
    void testIfValidationSuccessfulForExistingUserWithNewUniqueEmail() {
        when(user.getId()).thenReturn(id);
        when(userDAO.findOne(id)).thenReturn(oldUser);
        when(user.getEmail()).thenReturn(NOT_NULL_EMAIL);
        when(userDAO.findByEmail(NOT_NULL_EMAIL)).thenReturn(null);

        UserValidatorImpl userValidation = new UserValidatorImpl(userDAO);

        Assertions.assertTrue(userValidation.isUserValidForUpdate(user));
    }

    @Test
    void ifThrowsExceptionForUpdatingAbsentUser() {
        when(user.getId()).thenReturn(id);
        when(user.isDeleted()).thenReturn(true);
        when(userDAO.findOne(id)).thenReturn(null, user);
        when(user.getEmail()).thenReturn(NOT_NULL_EMAIL);

        Assertions.assertThrows(NotFoundResourceException.class, () -> userValidation.isUserValidForUpdate(user));
        Assertions.assertThrows(NotFoundResourceException.class, () -> userValidation.isUserValidForUpdate(user));
    }


    @Test
    void ifThrowsExceptionForUpdatingWithInvalidEmail() {
        when(user.getId()).thenReturn(id);
        when(userDAO.findOne(id)).thenReturn(oldUser);
        when(userDAO.findByEmail(anyString())).thenReturn(mock(User.class));
        when(user.getEmail()).thenReturn(absentEmail, NOT_NULL_EMAIL);
        when(oldUser.getEmail()).thenReturn(anotherExistingEmail);
        Assertions.assertThrows(InvalidRequestParameterException.class, () -> userValidation.isUserValidForUpdate(user));
        Assertions.assertThrows(InvalidRequestParameterException.class, () -> userValidation.isUserValidForUpdate(user));

    }

    @Test
    public void testIsUnavailable() {
        setValidUser();
        assertFalse(userValidation.isUnavailable(validUser));
        assertTrue(userValidation.isUnavailable(null));
        validUser.setDeleted(true);
        assertTrue(userValidation.isUnavailable(validUser));
    }


}