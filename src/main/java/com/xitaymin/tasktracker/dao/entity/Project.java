package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@NamedQueries({@NamedQuery(name = Project.FIND_BY_ID_WITH_TEAMS,
        query = "SELECT p FROM Project p LEFT JOIN FETCH p.teams WHERE p.id=:id")})
public class Project extends BaseEntity {

    public static final String FIND_BY_ID_WITH_TEAMS = "Project.findByIdWithTeams";

    @Column(nullable = false)
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User productOwner;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "project_team", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private Set<Team> teams = new HashSet<>();

    public Project() {
    }

    public Project(String name, User productOwner) {
        this.name = name;
        this.productOwner = productOwner;
    }

    public User getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(User productOwner) {
        this.productOwner = productOwner;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    private void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTeam(Team team) {
        this.getTeams().add(team);
        team.getProjects().add(this);
    }

    public void removeTeam(Team team) {
        this.getTeams().remove(team);
        team.getProjects().remove(this);
    }

}
