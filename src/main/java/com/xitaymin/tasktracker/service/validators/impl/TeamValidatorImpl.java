package com.xitaymin.tasktracker.service.validators.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;
import com.xitaymin.tasktracker.service.exceptions.BaseApplicationException;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.TeamValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

//Удалить команду можно только если у неё нет проектов и участников
//Добавление пользователя в команду, пользователь может быть только в одной команде.
// Юзеров с ролями MANAGER и ADMIN нельзя добавлять в команды.
//Назначить лида команды. Он должен быть частью команды на момент назначения и иметь роль LEAD.
@Service
public class TeamValidatorImpl implements TeamValidator {
    public static final String TOO_MANY_MEMBERS =
            "Number of team's members can't be greater than max members number which is %d.";
    public static final String INVALID_ROLE = "Users with roles MANAGER or ADMIN can't be added to the team.";
    public static final String TEAM_NOT_FOUND = "Team with id = %d doesn't exist.";
    public static final String USER_HAS_ANOTHER_TEAM = "User with id = %d already consists in another team.";
    public static final String TEAM_IN_PROJECT = "Can't delete team which is in project.";
    public static final String TEAM_HAS_MEMBERS = "Can't delete team with members.";
    private final TeamDao teamDao;
    @Value("${task-tracker.max.team.members.count}")
    private int maxMembersNumber;

    public TeamValidatorImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public void validateForSave(CreateTeamTO createTeamTO,
                                @Value("${task-tracker.max.team.members.count}") int maxMembersNumber) {
//        int membersNumber = createTeamTO.getMembers().size();
//        if (membersNumber > maxMembersNumber) {
//            throw new TeamMembersNumberExceedException(String.format(TOO_MANY_MEMBERS,
//                    membersNumber,
//                    maxMembersNumber));
//        }

    }

    @Override
    public void validateForDelete(Team team) {
        boolean inProject = team.getProjects().isEmpty();
        if(inProject){
            throw new InvalidRequestParameterException(TEAM_IN_PROJECT);
        }
        boolean hasMembers = team.getMembers().isEmpty();
        if(hasMembers){
            throw new InvalidRequestParameterException(TEAM_HAS_MEMBERS);
        }

    }

    @Override
    public Team validateForUpdate(EditTeamTO editTeamTO) {
        Team team = teamDao.findById(editTeamTO.getId());
        if (team == null) {
            throw new NotFoundResourceException(String.format(TEAM_NOT_FOUND, editTeamTO.getId()));
        }
        return team;
    }

    //Назначить лида команды. Он должен быть частью команды на момент назначения и иметь роль LEAD.
    @Override
    public void validateForAddMember(Team team, User user) {
        if (team.getMembers().size() == maxMembersNumber) {
            throw new BaseApplicationException(String.format(TOO_MANY_MEMBERS, maxMembersNumber));
        } else if (user.isDeleted()) {
            throw new NotFoundResourceException(String.format(UserValidatorImpl.USER_NOT_FOUND, user.getId()));
        } else if (user.getTeam() != null) {
            throw new BaseApplicationException(String.format(USER_HAS_ANOTHER_TEAM, user.getId()));
        } else if (isUserRoleInvalid(user)) {
            throw new BaseApplicationException(INVALID_ROLE);
        }

        //todo check if the team already has lead
    }

    private boolean isUserRoleInvalid(User user) {
        Set<Role> roles = user.getRoles();
        return (roles.contains(Role.ADMIN)) || (roles.contains(Role.MANAGER));
    }
}
