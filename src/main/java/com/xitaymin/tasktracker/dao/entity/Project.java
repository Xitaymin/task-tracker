package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Добавить/удалить команду в проект.
//Назначить productOwner, он должен иметь роль MANAGER

@Entity
@Table(name = "projects")
@NamedQueries({@NamedQuery(name = Project.FIND_BY_ID_WITH_TEAMS, query = "SELECT p FROM Project p INNER JOIN p.teams team where p.id=:id")})
public class Project extends PersistentObject {

    public static final String FIND_BY_ID_WITH_TEAMS = "Project.findByIdWithTeams";
    private String name;
    @ManyToOne
    private User productOwner;

    @ManyToMany
    @JoinTable(
            name="project_team",
            joinColumns=@JoinColumn(name="project_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="team_id", referencedColumnName="id"))
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
