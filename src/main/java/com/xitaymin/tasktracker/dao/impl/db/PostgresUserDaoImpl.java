package com.xitaymin.tasktracker.dao.impl.db;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

//Пользователь (User)
//        CRUD для пользователя. Email пользователя должен быть уникальным.
//        Добавление/удаление ролей. При удалении роли убедиться, что не нарушаются инварианты других сущностей.
//        Редактирование пользователя. Редактировать можно всё кроме ID. Редактирование не затрагивает список команд
//        Удаление пользователя. При удалении пользователя, он фактически остается, но помечается как deleted=true. Все операции с участием юзера в других сущностях доступны только для deleted=false.
//        Получение пользователя по ID вместе с его тасками и командами.
//        Получение всех пользователей.

@Repository
public class PostgresUserDaoImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByEmail(String email) {
        User user = null;
        try {
            user = entityManager.createNamedQuery(User.FIND_BY_EMAIL, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
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

}
