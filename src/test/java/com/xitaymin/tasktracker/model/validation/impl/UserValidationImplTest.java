package com.xitaymin.tasktracker.model.validation.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserValidationImplTest {

    private final UserDAO userDAO = mock(UserDAOImpl.class);
    private final User user = mock(User.class);
    private final User oldUser = mock(User.class);
    private final UserValidationImpl userValidation = new UserValidationImpl(userDAO);

    private final long id = 1;
    private final String notNullEmail = "example@gmail.com";
    private final String anotherExistingEmail = "another@gmail.com";
    private final String absentEmail = null;

    @Test
    void testIfValidationSuccessfulForExistingUserWithNewUniqueEmail() {
        when(user.getId()).thenReturn(id);
        when(userDAO.findOne(id)).thenReturn(oldUser);
        when(user.getEmail()).thenReturn(notNullEmail);
        when(userDAO.findByEmail(notNullEmail)).thenReturn(null);

        UserValidationImpl userValidation = new UserValidationImpl(userDAO);

        Assertions.assertTrue(userValidation.isUserValidForUpdate(user));
    }

    @Test
    void ifThrowsExceptionForUpdatingAbsentUser() {
        when(user.getId()).thenReturn(id);
        when(user.isDeleted()).thenReturn(true);
        when(userDAO.findOne(id)).thenReturn(null, user);
        when(user.getEmail()).thenReturn(notNullEmail);

        Assertions.assertThrows(NotFoundResourceException.class, () -> userValidation.isUserValidForUpdate(user));
        Assertions.assertThrows(NotFoundResourceException.class, () -> userValidation.isUserValidForUpdate(user));
    }


    @Test
    void ifThrowsExceptionForUpdatingWithInvalidEmail() {
        when(user.getId()).thenReturn(id);
        when(userDAO.findOne(id)).thenReturn(oldUser);
        when(userDAO.findByEmail(anyString())).thenReturn(mock(User.class));
        when(user.getEmail()).thenReturn(absentEmail, notNullEmail);
        when(oldUser.getEmail()).thenReturn(anotherExistingEmail);
        //todo
        Assertions.assertThrows(InvalidRequestParameterException.class, () -> userValidation.isUserValidForUpdate(user));
        Assertions.assertThrows(InvalidRequestParameterException.class, () -> userValidation.isUserValidForUpdate(user));
        //        Assertions.assertThrows(InvalidRequestParameterException.class,()->{userValidation.isUserValidForUpdate(user);});

    }

}