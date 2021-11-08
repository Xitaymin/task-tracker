package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedQueries({@NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u"),
               @NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email=:email"),
               @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id")})

public class User extends PersistentObject {
    public static final String FIND_ALL = "User.getAll";
    public static final String DELETE = "User.delete";
    public static final String FIND_BY_EMAIL = "User.findByEmail";
    private String name;
    private String email;
    private boolean deleted;
    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {
    }

    public User(long id, @NotBlank String name, @NotNull @Email String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
    }
//todo find way to use EnumSet

//    todo UserViewTO
//    private Team team;
//    private List<Task> tasks;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

//    public User(long id, @NotBlank String name, @NotNull @Email String email, boolean deleted, Set<String> roles, Team team, List<Task> tasks) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.deleted = deleted;
//        this.roles = roles;
//        this.team = team;
//        this.tasks = tasks;
//    }

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


//
//    public Team getTeam() {
//        return team;
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }
//
//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return deleted == user.deleted && name.equals(user.name) && email.equals(user.email) && Objects.equals(roles,
//                user.roles) && Objects.equals(team, user.team) && Objects.equals(tasks, user.tasks);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, email, deleted, roles, team, tasks);
//    }
}
