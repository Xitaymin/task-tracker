package com.xitaymin.tasktracker.service.validators.impl;

import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.service.exceptions.BaseApplicationException;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.TeamValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.xitaymin.tasktracker.dao.entity.Role.LEAD;
import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;
import static java.lang.String.format;

@Service
public class TeamValidatorImpl implements TeamValidator {
    public static final String TOO_MANY_MEMBERS =
            "Number of team's members can't be greater than max members number which is %d.";
    public static final String INVALID_ROLE = "Users with roles MANAGER or ADMIN can't be added to the team.";
    public static final String USER_HAS_ANOTHER_TEAM = "User with id = %d already consists in another team.";
    public static final String TEAM_IN_PROJECT = "Can't delete team which is in project.";
    public static final String TEAM_HAS_MEMBERS = "Can't delete team with members.";
    public static final String TEAM_ALREADY_HAS_LEAD = "User has role LEAD and can't be added to team, because it already has one.";

    private final int maxMembersNumber;

    public TeamValidatorImpl(@Value("${task-tracker.max.team.members.count}") int maxMembersNumber) {
        this.maxMembersNumber = maxMembersNumber;
    }

    @Override
    public void validateForDelete(Team team) {
        boolean inProject = !team.getProjects().isEmpty();
        if (inProject) {
            throw new InvalidRequestParameterException(TEAM_IN_PROJECT);
        }
        boolean hasMembers = !team.getMembers().isEmpty();
        if (hasMembers) {
            throw new InvalidRequestParameterException(TEAM_HAS_MEMBERS);
        }
    }

    @Override
    public void validateForAddMember(Team team, User user) {
        Set<User> members = team.getMembers();
        if (members.size() == maxMembersNumber) {
            throw new BaseApplicationException(format(TOO_MANY_MEMBERS, maxMembersNumber));
        }
        if (user.isDeleted()) {
            throw new NotFoundResourceException(format(USER_NOT_FOUND, user.getId()));
        }
        if (user.getTeam() != null) {
            throw new BaseApplicationException(format(USER_HAS_ANOTHER_TEAM, user.getId()));
        }
        if (isUserRoleInvalid(user)) {
            throw new BaseApplicationException(INVALID_ROLE);
        }
        if (user.getRoles().contains(LEAD)) {
            validateIfLeadAlreadyPresent(members);
        }
    }

    @Override
    public void validateIfLeadAlreadyPresent(Set<User> members) {
        boolean foundLead = members.stream()
                .flatMap(member -> member.getRoles().stream())
                .anyMatch(LEAD::equals);

        if (foundLead) throw new InvalidRequestParameterException(TEAM_ALREADY_HAS_LEAD);
    }

    private boolean isUserRoleInvalid(User user) {
        Set<Role> roles = user.getRoles();
        return (roles.contains(Role.ADMIN)) || (roles.contains(Role.MANAGER));
    }
}
