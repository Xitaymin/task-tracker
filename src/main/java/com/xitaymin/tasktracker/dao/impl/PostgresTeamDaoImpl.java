package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Team;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public class PostgresTeamDaoImpl implements TeamDao {
    @Override
    public Team findByIdWithMembers(long teamId) {
        Team team = null;
        try {
            team = entityManager.createNamedQuery(Team.FIND_TEAM_WITH_MEMBERS_BY_ID, Team.class)
                    .setParameter("id", teamId)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return team;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Team save(Team team) {
        entityManager.persist(team);
        return team;
    }

    @Override
    public Team findById(long id) {
        return entityManager.find(Team.class, id);
    }

    @Override
    public void update(Team team) {
        entityManager.merge(team);
    }

    @Override
    public Collection<Team> findAll() {
        return entityManager.createNamedQuery(Team.FIND_ALL, Team.class).getResultList();
    }

    @Override
    public Collection<Team> findAllWithMembersAndProjects() {
        return entityManager.createNamedQuery(Team.FIND_ALL_WITH_MEMBERS_AND_PROJECTS, Team.class).getResultList();
    }

    @Override
    public Team findByIdWithMembersAndProjects(long id) {
        Team team = null;
        try {
            team = entityManager.createNamedQuery(Team.FIND_TEAM_WITH_MEMBERS_AND_PROJECTS_BY_ID, Team.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public void delete(Team team) {
        entityManager.remove(team);
    }

}

