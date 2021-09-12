package com.xitaymin.tasktracker.dao;

import java.util.Collection;

public interface GenericDAO<T> {
    T save(final T entity);

    T update(final T entity);

    void delete(final long id);

    void deleteAll();

    T findOne(final long id);

    Collection<T> findAll();

}
