package com.xitaymin.tasktracker.model.validation;

import com.xitaymin.tasktracker.dao.entity.User;

public interface UserValidation {

    boolean isUserValidForSave(User user);

    boolean isUserValidForUpdate(User user);

    boolean isUnavailable(User user);
}
