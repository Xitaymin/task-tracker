package com.xitaymin.tasktracker.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@Getter
@Entity
@NamedQueries({
        @NamedQuery(name = Team.FIND_ALL, query = "SELECT t FROM Team t order by t.id"),
        @NamedQuery(name = Team.FIND_TEAM_WITH_MEMBERS_AND_PROJECTS_BY_ID,
                query = "SELECT  t FROM Team t LEFT JOIN FETCH t.members LEFT JOIN FETCH t.projects WHERE t.id=:id"),
        @NamedQuery(name = Team.FIND_TEAM_WITH_MEMBERS_BY_ID,
                query = "SELECT  t FROM Team t LEFT JOIN FETCH t.members WHERE t.id=:id"),
        @NamedQuery(name = Team.FIND_ALL_TEAMS_WITH_MEMBERS,
                query = "SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.members")})
public class Team extends BaseEntity {

    public static final String FIND_ALL = "Team.findAll";
    public static final String FIND_TEAM_WITH_MEMBERS_AND_PROJECTS_BY_ID = "Team.findTeamWithMembersAndProjectsById";
    public static final String FIND_TEAM_WITH_MEMBERS_BY_ID = "Team.findTeamWithMembersById";
    public static final String FIND_ALL_TEAMS_WITH_MEMBERS = "Team.findAllTeamsWithMembers";

    @Setter
    @Column(nullable = false)
    private String name;
    @Setter(value = PRIVATE)
    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> members = new HashSet<>();
    @Setter(value = PRIVATE)
    @ManyToMany(mappedBy = "teams")
    private Set<Project> projects = new HashSet<>();

    public Team(String name) {
        this.name = name;
    }

    public void addMember(User member) {
        this.getMembers().add(member);
        member.setTeam(this);
    }
}
