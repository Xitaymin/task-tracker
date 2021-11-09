package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.Entity;

//Команда (Team)
//CRUD для команды.
//Редактировать можно только имя.
//Удалить команду можно только если у неё нет проектов и участников
//Добавление пользователя в команду, пользователь может быть только в одной команде.
// Максимальный размер команды определен в пропертях. Юзеров с ролями MANAGER и ADMIN нельзя добавлять в команды.
//Назначить лида команды. Он должен быть частью команды на момент назначения и иметь роль LEAD.

@Entity
public class Team extends PersistentObject {

    private String name;
//    @OneToMany
//    private List<User> members;

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

//    public List<User> getMembers() {
//        return members;
//    }
//
//    public void setMembers(List<User> members) {
//        this.members = members;
//    }
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
