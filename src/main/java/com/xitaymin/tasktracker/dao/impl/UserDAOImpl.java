package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.utils.Resetable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserDAOImpl implements UserDAO, Resetable {
    private final AtomicLong autoID = new AtomicLong(1);
    private final Map<Long, User> usersById = new HashMap<>();
    private final Map<String, User> usersByEmail = new HashMap<>();

    @Override
    public User findByEmail(String email) {
        return usersByEmail.get(email);
    }

    @Override
    public User save(User user) {
        long id = autoID.getAndIncrement();
        user.setId(id);
        user.setDeleted(false);
        usersById.put(id, user);
        usersByEmail.put(user.getEmail(), user);
        return user;
    }

    @Override
    public User update(User user) {
        usersByEmail.put(user.getEmail(), user);
        return usersById.put(user.getId(), user);
    }

    @Override
    public User findOne(long id) {
        return usersById.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(usersById.values());
    }

    @Override
    public void reset() {
        autoID.set(1);
        usersById.clear();
        usersByEmail.clear();
    }
}
