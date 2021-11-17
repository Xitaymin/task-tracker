package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Team;

import java.util.Collection;

public interface TeamDao {
    Team save(Team team);

    Team findById(long id);

    void update(Team team);

    Collection<Team> findAll();

    Collection<Team> findAllWithMembersAndProjects();

    Team findByIdWithMembersAndProjects(long id);

    void delete(Team id);

    Team findByIdWithMembers(long teamId);
}
