package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskDAOImpl implements TaskDAO {
    public static final Logger LOGGER =
            LoggerFactory.getLogger(TaskDAOImpl.class);

    private final AtomicLong autoID;
    private final Map<Long, Task> tasks;

    public TaskDAOImpl(AtomicLong autoID, Map<Long, Task> tasks) {
        this.autoID = autoID;
        this.tasks = tasks;
    }

    @Override
    public Task save(Task task) {
        Long id = autoID.getAndIncrement();
        task.setId(id);
        tasks.put(id, task);
        LOGGER.debug(task.toString());
        return task;
    }

    @Override
    public Task update(Task entity) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public Task findOne(Long id) {
        return tasks.get(id);
    }

    @Override
    public Collection<Task> findAll() {
        return tasks.values();
    }
}
