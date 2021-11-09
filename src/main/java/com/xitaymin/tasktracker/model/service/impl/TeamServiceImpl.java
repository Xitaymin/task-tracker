package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.service.TeamService;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Team saveTeam(CreateTeamTO createTeamTO) {
        Team team = new Team();
        team.setName(createTeamTO.getName());
        return teamDao.save(team);
    }
}
