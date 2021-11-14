package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

//todo difference beetween left join and left join fetch
@Entity
@Table(name = "users")
@NamedQueries({@NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u"),
               @NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email=:email"),
               @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
               @NamedQuery(name = User.FIND_BY_ID_WITH_ALL, query = "SELECT u FROM User u LEFT JOIN FETCH u.tasks  LEFT JOIN FETCH u.team WHERE u.id=:id")})

public class User extends PersistentObject {
    public static final String FIND_ALL = "User.getAll";
    public static final String DELETE = "User.delete";
    public static final String FIND_BY_EMAIL = "User.findByEmail";
    public static final String FIND_BY_ID_WITH_ALL = "User.findByIdWithTasksAndTeams";

    private String name;
    private String email;
    private boolean deleted;
    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.MERGE)
    private Set<Task> tasks;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    //todo don't show emails on view
    public User() {
    }

    public User(String name, String email, boolean deleted, Set<Role> roles, Set<Task> tasks, Team team) {
        this.name = name;
        this.email = email;
        this.deleted = deleted;
        this.roles = roles;
        this.tasks = tasks;
        this.team = team;
    }

    //todo find way to use EnumSet

    //    todo UserViewTO


    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
