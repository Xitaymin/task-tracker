package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.exception.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.UserService;
import com.xitaymin.tasktracker.model.validation.UserValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final UserValidation userValidation;

    public UserServiceImpl(UserDAO userDAO, UserValidation userValidation) {
        this.userDAO = userDAO;
        this.userValidation = userValidation;
    }

    @Override
    public User save(User user) {
        if (userValidation.isUserValidForSave(user)) {
            return userDAO.save(user);
        } else {
            //todo fix this
            return null;
        }
    }


    @Override
    public void editUser(User user) {
        if (userValidation.isUserValidForUpdate(user)) {
            userDAO.update(user);
        }

    }

    @Override
    public void deleteUser(long id) {
        User user = userDAO.findOne(id);
        if (userValidation.isUnavailable(user)) {
            throw new NotFoundResourceException(String.format("User with id = %s wasn't found", id));
        } else {
            user.setDeleted(true);
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return userDAO.findAll().stream().
                filter(e -> !e.isDeleted()).
                collect(Collectors.toCollection(ArrayList::new));
    }

}
