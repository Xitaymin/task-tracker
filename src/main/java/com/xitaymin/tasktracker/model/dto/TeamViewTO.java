package com.xitaymin.tasktracker.model.dto;

import com.xitaymin.tasktracker.model.dto.user.EditUserTO;

import java.util.Set;

public class TeamViewTO {
    private final String name;
    private final Set<EditUserTO> members;

    public TeamViewTO(String name, Set<EditUserTO> members) {
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public Set<EditUserTO> getMembers() {
        return members;
    }


}
