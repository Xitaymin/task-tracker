package com.xitaymin.tasktracker.dto.project;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.User;

public final class ProjectBuilder {
    protected long id;
    private String name;
    private User productOwner;

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

    public ProjectBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public Project build() {
        Project project = new Project();
        project.setName(name);
        project.setProductOwner(productOwner);
        project.setId(id);
        return project;
    }
}
