package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dao.entity.PersistentObject;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;

public abstract class GenericService {
    public void throwExceptionIfAbsent(String message, PersistentObject entity, long id) {
        if (entity == null) {
            throw new NotFoundResourceException(String.format(message, id));
        }
    }
}
