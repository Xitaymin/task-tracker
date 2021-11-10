package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.TeamViewTO;
import com.xitaymin.tasktracker.model.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.model.dto.team.EditTeamTO;

import java.util.Collection;

public interface TeamService {
    Team saveTeam(CreateTeamTO team);

    void editTeam(EditTeamTO editTeamTO);

    TeamViewTO getTeam(long id);

    Collection<TeamViewTO> getAllTeams();

    void deleteTeam(long id);

    void addMember(long team, long user);
}
