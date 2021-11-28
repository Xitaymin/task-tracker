package com.xitaymin.tasktracker.web.dto;

import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;

import java.util.Set;

public class UserAdminView {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;

    public UserAdminView() {
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreateUserTO toCreateTO() {
        return new CreateUserTO(this.name, this.email, this.roles);
    }

    public static UserAdminView of(UserViewTO userViewTO) {
        UserAdminView userView = new UserAdminView();
        userView.setId(userViewTO.getId());
        userView.setName(userViewTO.getName());
        userView.setEmail(userViewTO.getEmail());
        userView.setRoles(userViewTO.getRoles());
        return new UserAdminView();

    }
}
