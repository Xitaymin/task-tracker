package com.xitaymin.tasktracker.model.validators;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.model.dto.user.EditUserTO;

public interface UserValidator {

    void validateForSave(CreateUserTO user);

    User validateForUpdate(EditUserTO user);

    boolean isUnavailable(User user);
}
