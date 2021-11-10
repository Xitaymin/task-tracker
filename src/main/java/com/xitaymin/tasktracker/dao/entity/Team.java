package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.util.List;

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
    private String name;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members;

//    private List<Project> projects;

    public Team() {
    }

//    public Team(long id, String name, List<User> members, List<Project> projects) {
//        this.id = id;
//        this.name = name;
//        this.members = members;
//        this.projects = projects;
//    }


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
//
//    public List<Project> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(List<Project> projects) {
//        this.projects = projects;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Team team = (Team) o;
//        return name.equals(team.name) && Objects.equals(members, team.members) && Objects.equals(projects,
//                team.projects);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, members, projects);
//    }
}
