package com.xitaymin.tasktracker.model.dto;

import java.util.Set;

public class CreateTeamTO {

    private String name;
    private Set<Integer> members;

    public CreateTeamTO(String name, Set<Integer> members) {
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getMembers() {
        return members;
    }

    public void setMembers(Set<Integer> members) {
        this.members = members;
    }

    //    private List<Project> projects;
}
