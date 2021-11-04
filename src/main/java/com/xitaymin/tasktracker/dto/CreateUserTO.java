package com.xitaymin.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUserTO {
    public final Long id;
    @NotBlank
    public final String name;
    @NotNull
    @Email
    public final String email;
    @JsonIgnore
    public final boolean deleted;


    public CreateUserTO(Long id, @NotBlank String name, @NotNull @Email String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
    }


}

