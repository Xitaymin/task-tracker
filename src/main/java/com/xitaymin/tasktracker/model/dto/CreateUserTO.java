package com.xitaymin.tasktracker.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xitaymin.tasktracker.dao.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUserTO {
    @NotBlank
    private final String name;
    @NotNull
    @Email
    private final String email;

    @JsonCreator
    public CreateUserTO(@JsonProperty("name") @NotBlank String name,
                        @JsonProperty("email") @NotNull @Email String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User convertToEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setDeleted(false);
        return user;
    }
}
