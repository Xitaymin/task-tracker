package com.xitaymin.tasktracker.model.validators;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.CreateUserTO;

public interface UserValidator {

    void validateForSave(CreateUserTO user);

    void validateForUpdate(User user);

    boolean isUnavailable(User user);
}
