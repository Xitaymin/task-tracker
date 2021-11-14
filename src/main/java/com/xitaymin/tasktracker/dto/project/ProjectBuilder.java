package com.xitaymin.tasktracker.dto.project;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;

import java.util.Set;

public final class ProjectBuilder {
    protected long id;
    private String name;
    private User productOwner;
    private Set<Team> teams;

    private ProjectBuilder() {
    }

    public static ProjectBuilder aProject() {
        return new ProjectBuilder();
    }

    public ProjectBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProjectBuilder withProductOwner(User productOwner) {
        this.productOwner = productOwner;
        return this;
    }

    public ProjectBuilder withTeams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public ProjectBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public Project build() {
        Project project = new Project();
        project.setName(name);
        project.setProductOwner(productOwner);
        project.setTeams(teams);
        project.setId(id);
        return project;
    }
}
