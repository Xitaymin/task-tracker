package com.xitaymin.tasktracker.model.validators.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.dto.EditTeamTO;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.TeamValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//Удалить команду можно только если у неё нет проектов и участников
//Добавление пользователя в команду, пользователь может быть только в одной команде.
// Юзеров с ролями MANAGER и ADMIN нельзя добавлять в команды.
//Назначить лида команды. Он должен быть частью команды на момент назначения и иметь роль LEAD.
@Service
public class TeamValidatorImpl implements TeamValidator {
    public static final String TOO_MANY_MEMBERS =
            "Number of team's members which is %d can't be greater than max members number which is %d.";
    private final TeamDao teamDao;
    public final String TEAM_NOT_FOUND = "Team with id = %d doesn't exist.";

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
    public void validateForDelete(long id) {

    }

    @Override
    public Team validateForUpdate(EditTeamTO editTeamTO) {
        Team team = teamDao.findById(editTeamTO.getId());
        if (team == null) {
            throw new NotFoundResourceException(String.format(TEAM_NOT_FOUND, editTeamTO.getId()));
        }
        return team;
    }
}
