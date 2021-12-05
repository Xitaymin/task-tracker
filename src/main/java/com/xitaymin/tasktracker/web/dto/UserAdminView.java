package com.xitaymin.tasktracker.web.dto;

import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.dto.user.EditUserTO;
import com.xitaymin.tasktracker.dto.user.FullUserTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

public class UserAdminView {
    @Positive
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
    private boolean deleted;
    @NotNull
    private Set<Role> roles;

    public UserAdminView() {
    }

    public static UserAdminView of(UserViewTO userViewTO) {
        UserAdminView userView = new UserAdminView();
        userView.setId(userViewTO.getId());
        userView.setName(userViewTO.getName());
        userView.setEmail(userViewTO.getEmail());
        userView.setRoles(userViewTO.getRoles());
        userView.setDeleted(userViewTO.isDeleted());
        return userView;
    }

    public static UserAdminView of(FullUserTO fullUserTO) {
        UserAdminView userAdminView = new UserAdminView();
        userAdminView.setId(fullUserTO.getId());
        userAdminView.setName(fullUserTO.getName());
        userAdminView.setEmail(fullUserTO.getEmail());
        userAdminView.setRoles(fullUserTO.getRoles());
        userAdminView.setDeleted(fullUserTO.isDeleted());
        return userAdminView;
    }

    public boolean getDeleted() {
        return deleted;
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public EditUserTO toEditTO() {
        return new EditUserTO(id, name, email);
    }
}
