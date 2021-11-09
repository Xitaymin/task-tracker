package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.dto.EditTeamTO;

import java.util.Collection;

public interface TeamService {
    Team saveTeam(CreateTeamTO team);

    void editTeam(EditTeamTO editTeamTO);

    Team getTeam(long id);

    Collection<Team> getAllTeams();

}
