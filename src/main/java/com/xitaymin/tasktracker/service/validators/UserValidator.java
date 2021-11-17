package com.xitaymin.tasktracker.service.validators;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.dto.user.EditUserTO;

public interface UserValidator {

    void validateForSave(CreateUserTO user);

    User getUserValidForUpdate(EditUserTO user);

    boolean isUnavailable(User user);
}
