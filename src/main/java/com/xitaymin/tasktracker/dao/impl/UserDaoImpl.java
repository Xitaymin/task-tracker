package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByEmail(String email) {
        List<User> users = entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class)
                .setParameter("email", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    @Transactional
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User findOne(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Collection<User> findAll() {
        return entityManager.createNamedQuery(User.FIND_ALL, User.class).getResultList();
    }

    @Override
    public User findFullUserById(long id) {
        List<User> users = entityManager.createNamedQuery(User.FIND_BY_ID_WITH_ALL, User.class)
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }
}
