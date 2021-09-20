package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserDAOImpl implements UserDAO {
    private final AtomicLong autoID = new AtomicLong(1);
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        Long id = autoID.getAndIncrement();
        user.setId(id);
        user.setDeleted(false);
        users.put(id, user);
        return user;
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public User findOne(Long id) {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
