package com.xitaymin.tasktracker.dao.entity;

import java.util.List;
import java.util.Objects;

public class Team {

    private long id;
    private String name;
    private List<User> members;
    private List<Project> projects;

    public Team() {
    }

    public Team(long id, String name, List<User> members, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.projects = projects;
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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return name.equals(team.name) && Objects.equals(members, team.members) && Objects.equals(projects,
                team.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, members, projects);
    }
}
