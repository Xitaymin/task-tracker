package com.xitaymin.tasktracker.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EditUserTO {
    @Positive
    private final long id;
    @NotBlank
    private final String name;
    @NotNull
    @Email
    private final String email;

    @JsonCreator
    public EditUserTO(@JsonProperty("id") @Positive long id, @JsonProperty("name") @NotBlank String name,
                      @JsonProperty("email") @NotNull @Email String email) {
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
