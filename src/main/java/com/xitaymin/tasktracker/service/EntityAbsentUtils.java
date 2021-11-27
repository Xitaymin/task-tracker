package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dao.entity.BaseEntity;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;

public final class EntityAbsentUtils {

    private EntityAbsentUtils() {
    }

    public static void throwExceptionIfAbsent(String message, BaseEntity entity, long id) {
        if (entity == null) {
            throw new NotFoundResourceException(String.format(message, id));
        }
    }
}
