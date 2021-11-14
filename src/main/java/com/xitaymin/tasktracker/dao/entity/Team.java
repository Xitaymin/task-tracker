package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

//D для команды.
//Удалить команду можно только если у неё нет проектов и участников
//Добавление пользователя в команду, пользователь может быть только в одной команде.
// Максимальный размер команды определен в пропертях. Юзеров с ролями MANAGER и ADMIN нельзя добавлять в команды.
//Назначить лида команды. Он должен быть частью команды на момент назначения и иметь роль LEAD.

@Entity
@NamedQueries({@NamedQuery(name = Team.FIND_ALL, query = "SELECT t FROM Team t order by t.id"),
               @NamedQuery(name = Team.FIND_TEAM_WITH_MEMBERS_BY_ID, query = "SELECT  t FROM Team t LEFT JOIN FETCH t" + ".members WHERE t.id=:id"),
               @NamedQuery(name = Team.FIND_ALL_WITH_MEMBERS, query = "SELECT  t FROM Team t LEFT JOIN FETCH t" + ".members")
//               @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id")
})
public class Team extends PersistentObject {

    public static final String FIND_ALL = "Team.findAll";
    public static final String FIND_ALL_WITH_MEMBERS = "Team.findAllWithMembers";
    public static final String FIND_TEAM_WITH_MEMBERS_BY_ID = "Team.findTeamWithMembersById";

    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> members;

    @ManyToMany(mappedBy = "teams")
    private Set<Project> projects;

    public Team() {
    }

    public Team(String name, List<User> members, Set<Project> projects) {
        this.name = name;
        this.members = members;
        this.projects = projects;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

}
