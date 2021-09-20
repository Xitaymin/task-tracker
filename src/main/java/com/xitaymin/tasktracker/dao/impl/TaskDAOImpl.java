package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskDAOImpl implements TaskDAO {
    private final AtomicLong autoID = new AtomicLong(1);
    private final Map<Long, Task> tasks = new HashMap<>();

    @Override
    public Task save(Task task) {
        Long id = autoID.getAndIncrement();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @Override
    public Task update(Task task) {
        return tasks.put(task.getId(), task);
    }

    @Override
    public Task findOne(Long id) {
        return tasks.get(id);
    }

    @Override
    public Collection<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }
}
