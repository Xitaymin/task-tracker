package com.xitaymin.tasktracker.dao;

import java.util.Collection;

public interface GenericDAO<T> {
    T save(final T entity);

    T update(final T entity);

    T findOne(final Long id);

    Collection<T> findAll();

}
