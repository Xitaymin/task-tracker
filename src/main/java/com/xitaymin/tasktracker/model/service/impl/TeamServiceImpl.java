package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.dto.EditTeamTO;
import com.xitaymin.tasktracker.model.service.TeamService;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.TeamValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    public static final String TEAM_NOT_FOUND = "Team with id = %d doesn't exist.";
    private final TeamDao teamDao;
    private final TeamValidator teamValidator;

    public TeamServiceImpl(TeamDao teamDao, TeamValidator teamValidator) {
        this.teamDao = teamDao;
        this.teamValidator = teamValidator;
    }

    @Override
    public Team saveTeam(CreateTeamTO createTeamTO) {
        Team team = new Team();
        team.setName(createTeamTO.getName());
        return teamDao.save(team);
    }

    @Transactional
    @Override
    public void editTeam(EditTeamTO editTeamTO) {
        Team team = teamValidator.validateForUpdate(editTeamTO);
        team.setName(editTeamTO.getName());
        teamDao.update(team);
    }

    @Override
    public Team getTeam(long id) {
        Optional<Team> team = Optional.ofNullable(teamDao.findById(id));
        return team.orElseThrow(() -> new NotFoundResourceException(String.format(TEAM_NOT_FOUND, id)));
    }

    @Override
    public Collection<Team> getAllTeams() {
        return teamDao.findAll();
    }
}
