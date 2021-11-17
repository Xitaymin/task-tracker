package com.xitaymin.tasktracker.dto;

import com.xitaymin.tasktracker.dto.user.EditUserTO;

import java.util.Set;

public class TeamViewTO {
    private final String name;
    private final Set<Long> members;
    private final Set<Long> projects;

    public TeamViewTO(String name, Set<Long> members, Set<Long> projects) {
        this.name = name;
        this.members = members;
        this.projects = projects;
    }

}
