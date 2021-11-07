package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks;
//    private List<Team> teams;

    public Project() {
    }

    //    public Project(long id, String name, List<Team> teams) {
//        this.id = id;
//        this.name = name;
//        this.teams = teams;
//    }
    public Project(long id, String name) {
        this.id = id;
        this.name = name;
        this.tasks = new HashSet<>();

//        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Team> getTeams() {
//        return teams;
//    }

//    public void setTeams(List<Team> teams) {
//        this.teams = teams;
//    }

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
