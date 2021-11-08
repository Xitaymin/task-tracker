package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.service.TeamService;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    @Override
    public Team saveTeam(CreateTeamTO team) {
        return null;
    }
}
