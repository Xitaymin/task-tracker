package com.xitaymin.tasktracker.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@NamedQueries({@NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u"),
               @NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email=:email"),
               @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id")})

public class User extends Item {
    public static final String FIND_ALL = "User.getAll";
    public static final String DELETE = "User.delete";
    public static final String FIND_BY_EMAIL = "User.findByEmail";
    //    @Id
//    //todo generation types description and allocation size
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
//    @SequenceGenerator(name = "users_seq", sequenceName = "SEQ_USER", allocationSize = 10)
//    private long id;
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
    @JsonIgnore
    private boolean deleted;

//    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private Set<String> roles;
//    private Team team;
//    private List<Task> tasks;

    public User() {
    }

    public User(long id, @NotBlank String name, @NotNull @Email String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
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

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
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

//    public Set<String> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<String> roles) {
//        this.roles = roles;
//    }
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
