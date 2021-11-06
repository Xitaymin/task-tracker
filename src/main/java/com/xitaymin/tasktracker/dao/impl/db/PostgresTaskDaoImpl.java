package com.xitaymin.tasktracker.dao.impl.db;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public class PostgresTaskDaoImpl implements TaskDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Task> findByAssignee(long id) {
        return entityManager.createNamedQuery(Task.FIND_BY_ASSIGNEE, Task.class)
                .setParameter("assignee", id)
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

    //todo change all id types to int
    @Override
    public Task findOne(long id) {
        return entityManager.find(Task.class, id);
    }

    @Override
    public Collection<Task> findAll() {
        return entityManager.createNamedQuery(Task.FIND_ALL, Task.class).getResultList();
    }
}
