package com.xitaymin.tasktracker.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class User {
    private long id;
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
    @JsonIgnore
    private boolean deleted;

    public User() {
    }

    public User(long id, String name, String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}
