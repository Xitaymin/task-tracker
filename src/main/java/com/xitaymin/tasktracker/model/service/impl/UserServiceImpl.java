package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.service.UserService;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.UserValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final UserValidator userValidator;

    public UserServiceImpl(UserDAO userDAO, UserValidator userValidator) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
    }

    @Override
    @Transactional
    public User save(User user) {
        userValidator.validateForSave(user);
        return userDAO.save(user);
    }


    @Override
    public void editUser(User user) {
        userValidator.validateForUpdate(user);
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = userDAO.findOne(id);
        if (userValidator.isUnavailable(user)) {
            throw new NotFoundResourceException(String.format(USER_NOT_FOUND, id));
        } else {
            user.setDeleted(true);
            userDAO.update(user);
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return userDAO.findAll().stream().
                filter(e -> !e.isDeleted()).
                collect(Collectors.toCollection(ArrayList::new));
    }

}
