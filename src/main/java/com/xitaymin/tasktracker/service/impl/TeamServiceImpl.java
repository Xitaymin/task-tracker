package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.BaseEntity;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.TeamViewTO;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;
import com.xitaymin.tasktracker.service.TeamService;
import com.xitaymin.tasktracker.service.validators.TeamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

import static com.xitaymin.tasktracker.service.utils.EntityAbsentUtils.throwExceptionIfAbsent;
import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    public static final String TEAM_NOT_FOUND = "Team with id = %d doesn't exist.";
    private final TeamDao teamDao;
    private final TeamValidator teamValidator;
    private final UserDAO userDao;

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
        return teamDao.findAllFullTeams()
                .stream()
                .map(this::convertToTO)
                .collect(toSet());
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

        Set<Long> projectsId = team.getProjects()
                .stream()
                .map(BaseEntity::getId)
                .collect(toSet());

        Set<Long> membersId = team.getMembers()
                .stream()
                .map(BaseEntity::getId)
                .collect(toSet());

        return new TeamViewTO(team.getId(), team.getName(), membersId, projectsId);
    }
}
