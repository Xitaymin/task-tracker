package com.xitaymin.tasktracker.dao.impl.db;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Team;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public class PostgresTeamDaoImpl implements TeamDao {

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
}
