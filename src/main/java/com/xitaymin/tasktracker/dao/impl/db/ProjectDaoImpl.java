package com.xitaymin.tasktracker.dao.impl.db;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.entity.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public Project save(Project project) {
        entityManager.persist(project);
        return project;
    }

    @Override
    public void update(Project project) {
        entityManager.merge(project);
    }

    @Override
    public Project findById(long id) {
        return entityManager.find(Project.class, id);
    }
}
