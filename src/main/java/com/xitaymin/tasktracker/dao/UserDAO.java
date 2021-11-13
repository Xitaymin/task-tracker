package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.User;

public interface UserDAO extends GenericDAO<User> {
    User findByEmail(String email);

    User findByIdWithTasksAndTeams(long id);
}
