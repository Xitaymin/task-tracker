package com.xitaymin.tasktracker.dto.project;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.User;

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
        Long ownerId = null;
        User owner = project.getProductOwner();
        if (project.getProductOwner() != null) {
            ownerId = owner.getId();
        }
        return new ProjectViewTO(project.getId(), project.getName(), ownerId);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getProductOwner() {
        return productOwner;
    }
}
