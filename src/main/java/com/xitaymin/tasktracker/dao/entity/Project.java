package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Set;

//Добавить/удалить команду в проект.
//Назначить productOwner, он должен иметь роль MANAGER
//Author a = em.createQuery("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = 1", Author.class).getSingleResult();

@Entity
@Table(name = "projects")
@NamedQueries({@NamedQuery(name = Project.FIND_BY_ID_WITH_TEAMS, query = "SELECT p FROM Project p JOIN FETCH p.teams " + "WHERE p.id=:id")})
public class Project extends PersistentObject {

    public static final String FIND_BY_ID_WITH_TEAMS = "Project.findByIdWithTeams";

    private String name;
    @ManyToOne
    private User productOwner;

    //todo check it
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "project_team", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private Set<Team> teams;

    public User getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(User productOwner) {
        this.productOwner = productOwner;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Project() {
    }

    public Project(String name, User productOwner, Set<Team> teams) {
        this.name = name;
        this.productOwner = productOwner;
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
