package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;

public interface TeamService {
    Team saveTeam(CreateTeamTO team);
}
