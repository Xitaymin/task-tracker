package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.TeamViewTO;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;
import com.xitaymin.tasktracker.service.TeamService;
import com.xitaymin.tasktracker.service.validators.TeamValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.xitaymin.tasktracker.service.utils.EntityAbsentUtils.throwExceptionIfAbsent;
import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

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
    public TeamViewTO saveTeam(CreateTeamTO createTeamTO) {
        Team team = new Team();
        team.setName(createTeamTO.getName());
        return convertToTO(teamDao.save(team));
    }

    @Transactional
    @Override
    public void editTeam(EditTeamTO editTeamTO) {
        long id = editTeamTO.getId();
        Team team = teamDao.findById(id);
        throwExceptionIfAbsent(TEAM_NOT_FOUND,team,id);
        team.setName(editTeamTO.getName());
    }

    @Override
    public TeamViewTO getTeam(long id) {
        Team team = teamDao.findFullTeamById(id);
        throwExceptionIfAbsent(TEAM_NOT_FOUND, team, id);
        return convertToTO(team);
    }

    @Override
    public Collection<TeamViewTO> getAllTeams() {
        Collection<Team> teams = teamDao.findAllFullTeams();

        Collection<TeamViewTO> teamViewTOS = new HashSet<>();
        for (Team team : teams) {
            teamViewTOS.add(convertToTO(team));
        }

        return teamViewTOS;
    }

    @Transactional
    @Override
    public void deleteTeam(long id) {
        Team team = teamDao.findFullTeamById(id);
        teamValidator.validateForDelete(team);
        teamDao.delete(team);
    }


    @Transactional
    @Override
    public void addMember(long teamId, long userId) {
        Team team = teamDao.findByIdWithMembers(teamId);
        throwExceptionIfAbsent(TEAM_NOT_FOUND,team,teamId);

        User user = userDao.findOne(userId);
        throwExceptionIfAbsent(USER_NOT_FOUND,user,userId);

        teamValidator.validateForAddMember(team, user);

        team.addMember(user);

    }

    @Transactional
    @Override
    public void setLead(long teamId, long userId) {
        Team team = teamDao.findByIdWithMembers(teamId);
        throwExceptionIfAbsent(TEAM_NOT_FOUND,team,teamId);

        teamValidator.validateIfLeadAlreadyPresent(team.getMembers());

        User user = userDao.findOne(userId);
        throwExceptionIfAbsent(USER_NOT_FOUND,user,userId);

        user.getRoles().add(Role.LEAD);
    }

    private TeamViewTO convertToTO(Team team) {
        Set<Long> projectsId = new HashSet<>();
        for (Project project : team.getProjects()) {
            projectsId.add(project.getId());
        }
        Set<Long> membersId = new HashSet<>();
        for (User user : team.getMembers()) {
            Long memberId = user.getId();
            membersId.add(memberId);
        }
        return new TeamViewTO(team.getId(), team.getName(), membersId, projectsId);
    }
}
