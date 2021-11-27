package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Task;

public interface TaskDAO extends GenericDAO<Task> {

    Task findFullTaskById(long taskId);
}
