package com.xitaymin.tasktracker.dao.impl;

import com.xitaymin.tasktracker.dao.TeamDao;
import com.xitaymin.tasktracker.dao.entity.Team;
import org.hibernate.annotations.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public class TeamDaoImpl implements TeamDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Team findByIdWithMembers(long teamId) {
        List<Team> teams = entityManager.createNamedQuery(Team.FIND_TEAM_WITH_MEMBERS_BY_ID, Team.class)
                .setParameter("id", teamId)
                .getResultList();
        return DataAccessUtils.singleResult(teams);
    }

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
    public Collection<Team> findAllFullTeams() {
        List<Team> teams = entityManager.createNamedQuery(Team.FIND_ALL_TEAMS_WITH_MEMBERS, Team.class)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        return entityManager.createQuery("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.projects WHERE t IN :teams",
                Team.class)
                .setParameter("teams", teams)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }

    @Override
    public Team findFullTeamById(long id) {
        List<Team> teams = entityManager.createNamedQuery(Team.FIND_TEAM_WITH_MEMBERS_AND_PROJECTS_BY_ID, Team.class)
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(teams);
    }

    @Override
    public void delete(Team team) {
        entityManager.remove(team);
    }

}

