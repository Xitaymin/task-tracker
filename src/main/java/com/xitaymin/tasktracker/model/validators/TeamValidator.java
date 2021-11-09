package com.xitaymin.tasktracker.model.validators;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.model.dto.CreateTeamTO;
import com.xitaymin.tasktracker.model.dto.EditTeamTO;
import org.springframework.beans.factory.annotation.Value;

public interface TeamValidator {
    void validateForSave(CreateTeamTO createTeamTO,
                         @Value("${task-tracker.max.team.members.count}") int maxMembersCount);

    void validateForDelete(long id);

    Team validateForUpdate(EditTeamTO editTeamTO);
}
