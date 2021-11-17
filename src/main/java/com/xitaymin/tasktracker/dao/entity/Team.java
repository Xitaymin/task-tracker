package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//D для команды.
//Удалить команду можно только если у неё нет проектов и участников
//Добавление пользователя в команду, пользователь может быть только в одной команде.
// Максимальный размер команды определен в пропертях. Юзеров с ролями MANAGER и ADMIN нельзя добавлять в команды.
//Назначить лида команды. Он должен быть частью команды на момент назначения и иметь роль LEAD.

@Entity
@NamedQueries({@NamedQuery(name = Team.FIND_ALL, query = "SELECT t FROM Team t order by t.id"),
        @NamedQuery(name = Team.FIND_TEAM_WITH_MEMBERS_AND_PROJECTS_BY_ID, query = "SELECT  t FROM Team t LEFT JOIN FETCH t.members LEFT JOIN FETCH t.projects WHERE t.id=:id"),
//        @NamedQuery(name = Team.FIND_ALL_WITH_MEMBERS_AND_PROJECTS, query = "SELECT  t FROM Team t LEFT JOIN FETCH t.members LEFT JOIN FETCH t.projects ORDER BY t.id")
        @NamedQuery(name = Team.FIND_ALL_WITH_MEMBERS_AND_PROJECTS, query = "SELECT  t FROM Team t LEFT JOIN FETCH t.members LEFT JOIN FETCH t.projects ORDER BY t.id"),
        @NamedQuery(name = Team.FIND_TEAM_WITH_MEMBERS_BY_ID, query = "SELECT  t FROM Team t LEFT JOIN FETCH t.members  ORDER BY t.id")
})
public class Team extends PersistentObject {

    public static final String FIND_ALL = "Team.findAll";
    public static final String FIND_ALL_WITH_MEMBERS_AND_PROJECTS = "Team.findAllWithMembersAndProjects";
    public static final String FIND_TEAM_WITH_MEMBERS_AND_PROJECTS_BY_ID = "Team.findTeamWithMembersAndProjectsById";
    public static final String FIND_TEAM_WITH_MEMBERS_BY_ID = "Team.findTeamWithMembersById";

    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> members = new HashSet<>();

    @ManyToMany(mappedBy = "teams")
    private Set<Project> projects = new HashSet<>();

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    private void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getMembers() {
        return members;
    }

    private void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addMember(User member){
        this.getMembers().add(member);
        member.setTeam(this);
    }


}
