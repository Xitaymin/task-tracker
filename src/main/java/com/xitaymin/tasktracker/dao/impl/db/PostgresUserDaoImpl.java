package com.xitaymin.tasktracker.dao.impl.db;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

public class PostgresUserDaoImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User findOne(long id) {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }
}
