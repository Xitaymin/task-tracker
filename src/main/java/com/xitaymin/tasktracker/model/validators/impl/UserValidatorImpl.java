package com.xitaymin.tasktracker.model.validators.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorImpl implements UserValidator {
    public static final String EMAIL_IN_USE = "Email %s is already in use.";
    private final UserDAO userDAO;

    public UserValidatorImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void validateForSave(User user) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            throw new InvalidRequestParameterException(String.format(EMAIL_IN_USE, user.getEmail()));
        }
    }

    @Override
    public boolean isUserValidForUpdate(User user) {
        long id = user.getId();
        User oldUser = userDAO.findOne(id);
        String email = user.getEmail();
        if (isUnavailable(oldUser)) {
            throw new NotFoundResourceException(String.format("User with id = %s doesn't exist.", id));
        } else if (!isEmailValidForUpdate(email, oldUser)) {
            throw new InvalidRequestParameterException(String.format("Email %s is already used", email));
        }
        return true;
    }

    @Override
    public boolean isUnavailable(User user) {
        return (user == null || user.isDeleted());
    }

    private boolean isEmailValidForUpdate(String email, User oldUser) {
        if (email != null) {
            return (userDAO.findByEmail(email) == null || email.equals(oldUser.getEmail()));
        } else {
            throw new InvalidRequestParameterException("Email shouldn't be null.");
        }
    }
}