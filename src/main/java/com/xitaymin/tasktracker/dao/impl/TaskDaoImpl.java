package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import org.hibernate.annotations.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDAO {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Task> findAllFullTasks() {
        return entityManager.createNamedQuery(Task.FIND_ALL_FULL_TASKS, Task.class)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }

    @Override
    @Transactional
    public Task save(Task task) {
        entityManager.persist(task);
        return task;
    }

    @Override
    @Transactional
    public Task update(Task task) {
        return entityManager.merge(task);
    }

    @Override
    public Task findOne(long id) {
        return entityManager.find(Task.class, id);
    }

    @Override
    public Collection<Task> findAll() {
        return entityManager.createNamedQuery(Task.FIND_ALL, Task.class).getResultList();
    }

    @Override
    public Task findFullTaskById(long taskId) {
        List<Task> tasks = entityManager.createNamedQuery(Task.FIND_FULL_TASK_BY_ID, Task.class)
                .setParameter("id", taskId)
                .getResultList();
        return DataAccessUtils.singleResult(tasks);
    }
}
