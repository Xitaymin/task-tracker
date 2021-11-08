package com.xitaymin.tasktracker.model.validators;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.CreateUserTO;
import com.xitaymin.tasktracker.model.dto.EditUserTO;

public interface UserValidator {

    void validateForSave(CreateUserTO user);

    User validateForUpdate(EditUserTO user);

    boolean isUnavailable(User user);
}
