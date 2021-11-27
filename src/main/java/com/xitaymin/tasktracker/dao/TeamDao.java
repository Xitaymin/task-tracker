package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Team;

import java.util.Collection;

public interface TeamDao {
    Team save(Team team);

    Team findById(long id);

    Collection<Team> findAllFullTeams();

    Team findFullTeamById(long id);

    void delete(Team id);

    Team findByIdWithMembers(long teamId);
}
