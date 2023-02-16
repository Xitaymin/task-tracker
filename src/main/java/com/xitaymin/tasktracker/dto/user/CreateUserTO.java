package com.xitaymin.tasktracker.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CreateUserTO {
    @NotBlank
    private final String name;
    @NotNull
    @Email
    private final String email;
    private final Set<Role> roles;

    @JsonCreator
    public CreateUserTO(@JsonProperty("name") @NotBlank String name,
                        @JsonProperty("email") @NotNull @Email String email, @JsonProperty("roles") Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User convertToEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setDeleted(false);
        user.getRoles().addAll(roles);
        return user;
    }
}
