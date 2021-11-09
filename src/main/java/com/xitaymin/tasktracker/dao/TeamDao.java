package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Team;

public interface TeamDao {
    Team save(Team team);

    Team findById(long id);

    void update(Team team);
}
