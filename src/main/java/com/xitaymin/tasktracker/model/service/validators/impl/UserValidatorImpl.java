package com.xitaymin.tasktracker.model.service.validators.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.model.dto.user.EditUserTO;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.validators.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorImpl implements UserValidator {
    public static final String EMAIL_IN_USE = "Email %s is already in use.";
    public static final String USER_NOT_FOUND = "User with id = %s doesn't exist.";
    public static final String EMAIL_REQUIRED = "Email shouldn't be null.";
    private final UserDAO userDAO;

    public UserValidatorImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void validateForSave(CreateUserTO user) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            throw new InvalidRequestParameterException(String.format(EMAIL_IN_USE, user.getEmail()));
        }
        //todo role validation
    }

    @Override
    public User validateForUpdate(EditUserTO user) {
        long id = user.getId();
        User oldUser = userDAO.findOne(id);
        String email = user.getEmail();
        if (isUnavailable(oldUser)) {
            throw new NotFoundResourceException(String.format(USER_NOT_FOUND, id));
        } else if (!isEmailValidForUpdate(email, oldUser)) {
            throw new InvalidRequestParameterException(String.format(EMAIL_IN_USE, email));
        }
        return oldUser;
    }

    @Override
    public boolean isUnavailable(User user) {
        return (user == null || user.isDeleted());
    }

    private boolean isEmailValidForUpdate(String email, User oldUser) {
        if (email != null && !email.isBlank()) {
            return (userDAO.findByEmail(email) == null || email.equals(oldUser.getEmail()));
        } else {
            throw new InvalidRequestParameterException(EMAIL_REQUIRED);
        }
    }
}