package com.xitaymin.tasktracker.dto;

import java.util.Set;

public class TeamViewTO {
    private final long id;
    private final String name;
    private final Set<Long> members;
    private final Set<Long> projects;

    public TeamViewTO(long id, String name, Set<Long> members, Set<Long> projects) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.projects = projects;
    }

    public String getName() {
        return name;
    }

    public Set<Long> getMembers() {
        return members;
    }

    public Set<Long> getProjects() {
        return projects;
    }

    public long getId() {
        return id;
    }
}
