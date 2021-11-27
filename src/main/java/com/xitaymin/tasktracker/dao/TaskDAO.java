package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Task;

import java.util.List;

public interface TaskDAO extends GenericDAO<Task> {

    Task findFullTaskById(long taskId);

    List<Task> findAllFullTasks();
}
