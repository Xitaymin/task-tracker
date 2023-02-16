package com.xitaymin.tasktracker.service.validators.impl;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.service.exceptions.BaseApplicationException;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.xitaymin.tasktracker.dao.entity.Role.LEAD;
import static com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl.INVALID_ROLE;
import static com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl.TEAM_ALREADY_HAS_LEAD;
import static com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl.TEAM_HAS_MEMBERS;
import static com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl.TEAM_IN_PROJECT;
import static com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl.TOO_MANY_MEMBERS;
import static com.xitaymin.tasktracker.service.validators.impl.TeamValidatorImpl.USER_HAS_ANOTHER_TEAM;
import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamValidatorImplTest {
    private final int maxMembersNumber = 2;
    private final TeamValidatorImpl teamValidator = new TeamValidatorImpl(maxMembersNumber);
    private final EasyRandom easyRandom = new EasyRandom();

    private Team team;

    @BeforeEach
    void setUp() {
        team = mockEmptyTeam();
    }

    @Test
    @DisplayName("Validation for deletion fails with exception if team is in project")
    void validateDeletionForTeamInProject() {
        Project project = easyRandom.nextObject(Project.class);
        team.getProjects().add(project);

        checkExceptionTypeAndMessage(() -> teamValidator.validateForDelete(team),
                InvalidRequestParameterException.class,
                TEAM_IN_PROJECT);
    }

    @Test
    @DisplayName("Validation for deletion fails with exception if team contains members")
    void validateTeamDeletionWithPresentMembers() {
        User user = mockEmptyUser();
        team.getMembers().add(user);

        checkExceptionTypeAndMessage(() -> teamValidator.validateForDelete(team),
                InvalidRequestParameterException.class,
                TEAM_HAS_MEMBERS);
    }

    @Test
    @DisplayName("Validation for addition to team should fail if team already contains max number of members")
    void validateAdditionWithMaxMembersCount() {
        team.getMembers().add(mockEmptyUser());
        team.getMembers().add(mockEmptyUser());

        User newMember = mockEmptyUser();

        checkExceptionTypeAndMessage(() -> teamValidator.validateForAddMember(team, newMember),
                BaseApplicationException.class,
                format(TOO_MANY_MEMBERS, maxMembersNumber));
    }

    @Test
    @DisplayName("Validation for addition to team should fail for deleted user")
    void validateAdditionForDeletedUser() {
        User user = mockEmptyUser();
        user.setDeleted(true);

        checkExceptionTypeAndMessage(() -> teamValidator.validateForAddMember(team, user),
                NotFoundResourceException.class,
                format(USER_NOT_FOUND, user.getId()));
    }

    @Test
    @DisplayName("Validation for addition to team should fail for user from another team")
    void validateAdditionForUserFromOtherTeam() {
        User user = mockEmptyUser();

        Team otherTeam = mockEmptyTeam();
        user.setTeam(otherTeam);

        checkExceptionTypeAndMessage(() -> teamValidator.validateForAddMember(team, user),
                BaseApplicationException.class,
                format(USER_HAS_ANOTHER_TEAM, user.getId()));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "MANAGER"})
    @DisplayName("Validation for addition to team should fail for user with invalid role")
    void validateAdditionForUserWithInvalidRole(Role role) {
        User user = mockEmptyUser();
        user.getRoles().add(role);

        checkExceptionTypeAndMessage(() -> teamValidator.validateForAddMember(team, user),
                BaseApplicationException.class,
                INVALID_ROLE);
    }

    @Test
    @DisplayName("Validation for addition to team with LEAD should fail for user with LEAD role")
    void validateAdditionForUserWithLeadRole() {
        User oldLead = mockEmptyUser();
        oldLead.getRoles().add(LEAD);

        team.getMembers().add(oldLead);
        User user = mockEmptyUser();
        user.getRoles().add(LEAD);

        checkExceptionTypeAndMessage(() -> teamValidator.validateForAddMember(team, user),
                InvalidRequestParameterException.class,
                TEAM_ALREADY_HAS_LEAD);
    }

    private User mockEmptyUser() {
        User user = easyRandom.nextObject(User.class);
        user.setDeleted(false);
        user.setTeam(null);
        return user;
    }

    private Team mockEmptyTeam() {
        return easyRandom.nextObject(Team.class);
    }

    private static void checkExceptionTypeAndMessage(Executable action,
                                                     Class<? extends BaseApplicationException> expectedType,
                                                     String expectedMessage) {
        var actualException = assertThrows(expectedType, action);
        assertEquals(expectedType, actualException.getClass());
        assertEquals(expectedMessage, actualException.getMessage());
    }
}