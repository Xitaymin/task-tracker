package com.xitaymin.tasktracker.dto;

import com.xitaymin.tasktracker.dto.user.EditUserTO;

import java.util.Set;

public class TeamViewTO {
    private final String name;
    private final Set<EditUserTO> members;
    private final Set<Long> projects;

    public TeamViewTO(String name, Set<EditUserTO> members, Set<Long> projects) {
        this.name = name;
        this.members = members;
        this.projects = projects;
    }

}
