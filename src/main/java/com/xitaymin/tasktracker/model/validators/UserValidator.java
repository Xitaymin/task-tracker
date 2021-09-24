package com.xitaymin.tasktracker.model.validators;

import com.xitaymin.tasktracker.dao.entity.User;

public interface UserValidator {

    void validateForSave(User user);

    boolean isUserValidForUpdate(User user);

    boolean isUnavailable(User user);
}
