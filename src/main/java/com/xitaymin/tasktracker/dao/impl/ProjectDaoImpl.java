package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.entity.Project;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

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
    public Project findById(long id) {
        return entityManager.find(Project.class, id);
    }

    @Override
    public Project findByIdWithTeams(long projectId) {
        List<Project> projects = entityManager.createNamedQuery(Project.FIND_BY_ID_WITH_TEAMS, Project.class)
                .setParameter("id", projectId)
                .getResultList();
        return DataAccessUtils.singleResult(projects);
    }

    @Override
    public List<Project> findAll() {
        return entityManager.createNamedQuery(Project.FIND_ALL, Project.class).getResultList();
    }
}
