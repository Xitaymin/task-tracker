package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserWithTasks;
import com.xitaymin.tasktracker.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOGGER =
            LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;
    private TaskDAO taskDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void save(User user) {
        String email = user.getEmail();
        if (isEmailUsed(email)) {
            throw new IllegalArgumentException(String.format("Email %s is already in use.", email));
        } else {
            userDAO.save(user);
        }
    }

    @Override
    public boolean editUser(User user) {
        return false;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userDAO.findOne(id);
        if (user == null || user.isDeleted()) {
            LOGGER.debug("User with id = {} wasn't found", id);
            throw new NoSuchElementException(String.format("User with id = %s wasn't found", id));
        } else {
            user.setDeleted(true);
            LOGGER.debug("User with id = {} was deleted", id);
        }
    }

    @Override
    public UserWithTasks getById(Long id) {
        //todo check if user exists
        User user = userDAO.findOne(id);
        if (user == null || user.isDeleted()) {
            throw new IllegalArgumentException(String.format("User with id = %s not found", id));
        } else {
            String stringId = id.toString();
        }
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return userDAO.findAll().stream().filter(e -> !e.isDeleted()).collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isEmailUsed(String email) {
        Collection<User> users = userDAO.findAll();
        if (users.size() == 0) {
            LOGGER.debug("Users list is empty.");
            return false;
        } else {
            return users.stream().anyMatch(e -> e.getEmail().equals(email));
        }
    }
}
