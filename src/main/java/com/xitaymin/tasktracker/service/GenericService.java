package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dao.entity.BaseEntity;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;

public abstract class GenericService {
    public void throwExceptionIfAbsent(String message, BaseEntity entity, long id) {
        if (entity == null) {
            throw new NotFoundResourceException(String.format(message, id));
        }
    }
}
