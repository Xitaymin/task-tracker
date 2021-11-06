package com.xitaymin.tasktracker.model.validators.impl;

import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.service.exceptions.TeamMembersNumberExceedException;
import com.xitaymin.tasktracker.model.validators.TeamValidator;
import org.springframework.beans.factory.annotation.Value;

//Удалить команду можно только если у неё нет проектов и участников
//Добавление пользователя в команду, пользователь может быть только в одной команде.
// Юзеров с ролями MANAGER и ADMIN нельзя добавлять в команды.
//Назначить лида команды. Он должен быть частью команды на момент назначения и иметь роль LEAD.
public class TeamValidatorImpl implements TeamValidator {
    private static final String TOO_MANY_MEMBERS =
            "Number of team's members which is %d can't be greater than max members number which is %d.";

    @Override
    public void validateForSave(CreateTeamTO createTeamTO,
                                @Value("${task-tracker.max.team.members.count}") int maxMembersNumber) {
        int membersNumber = createTeamTO.getMembers().size();
        if (membersNumber > maxMembersNumber) {
            throw new TeamMembersNumberExceedException(String.format(TOO_MANY_MEMBERS,
                    membersNumber,
                    maxMembersNumber));
        }

    }

    @Override
    public void validateForDelete(long id) {

    }
}
