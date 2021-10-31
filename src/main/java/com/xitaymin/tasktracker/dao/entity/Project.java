package com.xitaymin.tasktracker.dao.entity;

import java.util.List;
import java.util.Objects;

public class Project {

    private long id;
    private String name;
    private List<Team> teams;

    public Project() {
    }

    public Project(long id, String name, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.teams = teams;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return name.equals(project.name) && teams.equals(project.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teams);
    }

}
