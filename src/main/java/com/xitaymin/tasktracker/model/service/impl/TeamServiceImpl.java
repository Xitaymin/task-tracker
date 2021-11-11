package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.TeamViewTO;
import com.xitaymin.tasktracker.model.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.model.dto.team.EditTeamTO;
import com.xitaymin.tasktracker.model.dto.user.EditUserTO;
import com.xitaymin.tasktracker.model.service.TeamService;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.validators.TeamValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.xitaymin.tasktracker.model.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

@Service
public class TeamServiceImpl implements TeamService {
    public static final String TEAM_NOT_FOUND = "Team with id = %d doesn't exist.";
    private final TeamDao teamDao;
    private final TeamValidator teamValidator;
    private final UserDAO userDao;

    public TeamServiceImpl(TeamDao teamDao, TeamValidator teamValidator, UserDAO userDao) {
        this.teamDao = teamDao;
        this.teamValidator = teamValidator;
        this.userDao = userDao;
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
    public TeamViewTO getTeam(long id) {
        Optional<Team> optionalTeam = Optional.ofNullable(teamDao.findById(id));
        Team team = optionalTeam.orElseThrow(() -> new NotFoundResourceException(String.format(TEAM_NOT_FOUND, id)));
        return convertToTO(team);
    }

    @Override
    public Collection<TeamViewTO> getAllTeams() {
//        return teamDao.findAll();
        Collection<Team> teams = teamDao.findAllWithMembers();

        Collection<TeamViewTO> teamViewTOS = new HashSet<>();
        for (Team team : teams) {
            teamViewTOS.add(convertToTO(team));
        }
        return teamViewTOS;
    }

    @Transactional
    @Override
    public void deleteTeam(long id) {
        teamValidator.validateForDelete(id);
    }


    @Transactional
    @Override
    public void addMember(long teamId, long userId) {
        Optional<Team> optionalTeam = Optional.ofNullable(teamDao.findById(teamId));
        Team team =
                optionalTeam.orElseThrow(() -> new NotFoundResourceException(String.format(TEAM_NOT_FOUND, teamId)));
        Optional<User> optionalUser = Optional.ofNullable(userDao.findOne(userId));
        User user =
                optionalUser.orElseThrow(() -> new NotFoundResourceException(String.format(USER_NOT_FOUND, userId)));

        teamValidator.validateForAddMember(team, user);
        user.setTeam(team);
        team.getMembers().add(user);
        teamDao.update(team);
    }

    private TeamViewTO convertToTO(Team team) {
        Set<EditUserTO> userTOSet = new HashSet<>();
        for (User user : team.getMembers()) {
            EditUserTO userTO = new EditUserTO(user.getId(), user.getName(), user.getEmail());
            userTOSet.add(userTO);
        }
        return new TeamViewTO(team.getName(), userTOSet);
    }
}
