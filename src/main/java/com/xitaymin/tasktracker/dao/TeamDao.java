package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Team;

import java.util.Collection;

public interface TeamDao {
    Team save(Team team);

    Team findById(long id);

    void update(Team team);

    Collection<Team> findAll();
}
