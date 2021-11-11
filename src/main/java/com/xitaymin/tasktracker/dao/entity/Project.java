package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Добавить/удалить команду в проект.
//Назначить productOwner, он должен иметь роль MANAGER

@Entity
@Table(name = "projects")
public class Project extends PersistentObject {

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

    //    public Project(long id, String name, List<Team> teams) {
//        this.id = id;
//        this.name = name;
//        this.teams = teams;
//    }
//    public Project(long id, String name) {
//        this.id = id;
//        this.name = name;
//
////        this.teams = teams;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Project project = (Project) o;
//        return name.equals(project.name) && teams.equals(project.teams);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, teams);
//    }

}
