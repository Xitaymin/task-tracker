package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserTasks;
import com.xitaymin.tasktracker.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOGGER =
            LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public UserServiceImpl(UserDAO userDAO, TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    @Override
    public User save(User user) {
        String email = user.getEmail();
        if (isEmailUsed(email)) {
            throw new IllegalArgumentException(String.format("Email %s is already in use.", email));
        } else {
            return userDAO.save(user);
        }
    }

    @Override
    public User editUser(User user) {
        Long id = user.getId();
        if (id == null) {
            throw new IllegalArgumentException("Id shouldn't be null");
        } else {
            User oldUser = userDAO.findOne(id);
            if (oldUser == null || oldUser.isDeleted()) {
                throw new NoSuchElementException(String.format("User with id = %s doesn't exist.", id));
            } else {
                String newEmail = user.getEmail();
                if (newEmail != null) {
                    if (newEmail.equals(oldUser.getEmail()) || !isEmailUsed(newEmail)) {
                        userDAO.update(user);
                    } else {
                        throw new IllegalArgumentException(String.format("Email %s is already used", newEmail));
                    }
                } else {
                    throw new IllegalArgumentException("Email shouldn't be null.");
                }
            }
        }
        return user;
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
    public UserTasks getById(Long id) {
        List<Task> tasks;
        User user = userDAO.findOne(id);
        if (user == null || user.isDeleted()) {
            throw new IllegalArgumentException(String.format("User with id = %s not found", id));
        } else {
            tasks = taskDAO.findAll().stream().filter(t -> t.getAssignee().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        }
        return new UserTasks(user, tasks);
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
