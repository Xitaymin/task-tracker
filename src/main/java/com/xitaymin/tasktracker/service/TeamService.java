package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dto.TeamViewTO;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;

import java.util.Collection;

public interface TeamService {
    TeamViewTO saveTeam(CreateTeamTO team);

    void editTeam(EditTeamTO editTeamTO);

    TeamViewTO getTeam(long id);

    Collection<TeamViewTO> getAllTeams();

    void deleteTeam(long id);

    void addMember(long team, long user);

    void setLead(long team, long lead);
}
