package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskDAOImpl implements TaskDAO {
    private final AtomicLong autoID = new AtomicLong(1);
    private final Map<Long, Task> tasks = new HashMap<>();

    @Override
    public List<Task> findByAssignee(long id) {
        List<Task> tasksByAssignee = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getAssignee() == id) {
                tasksByAssignee.add(task);
            }
        }
        return tasksByAssignee;
    }

    @Override
    public Task save(Task task) {
        long id = autoID.getAndIncrement();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @Override
    public Task update(Task task) {
        return tasks.put(task.getId(), task);
    }

    @Override
    public Task findOne(long id) {
        return tasks.get(id);
    }

    @Override
    public Collection<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }
}
