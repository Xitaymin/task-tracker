package com.xitaymin.tasktracker.service.validators;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface TeamValidator {

    void validateForDelete(Team team);

    void validateForAddMember(Team team, User user);

    void validateIfLeadAlreadyPresent(Set<User> members);
}
