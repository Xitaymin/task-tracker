package com.xitaymin.tasktracker.model.dto.user;

public class UserTO {
    private final long id;
    private final String name;
    private final String email;

    public UserTO(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


}