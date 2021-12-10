package com.xitaymin.tasktracker.dto.project;

import com.xitaymin.tasktracker.dao.entity.Project;

public class ProjectViewTO {
    private final long id;
    private final String name;
    private final Long productOwner;

    public ProjectViewTO(long id, String name, Long productOwner) {
        this.id = id;
        this.name = name;
        this.productOwner = productOwner;
    }

    public static ProjectViewTO of(Project project) {
        return new ProjectViewTO(project.getId(), project.getName(), project.getProductOwner().getId());
    }
}
