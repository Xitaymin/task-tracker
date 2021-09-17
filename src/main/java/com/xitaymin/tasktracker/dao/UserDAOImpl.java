package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserDAOImpl implements UserDAO {
    public static final Logger LOGGER =
            LoggerFactory.getLogger(UserDAOImpl.class);
    private final AtomicLong autoID;
    private final Map<Long, User> users;

    public UserDAOImpl(AtomicLong autoID, Map<Long, User> users) {
        this.autoID = autoID;
        this.users = users;
    }

    @Override
    public User save(User user) {
        Long id = autoID.getAndIncrement();
        user.setId(id);
        user.setDeleted(false);
        users.put(id, user);

        LOGGER.debug(String.format("User %s was saved", user));
        return user;
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void deleteAll() {
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
