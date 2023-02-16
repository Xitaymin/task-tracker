package com.xitaymin.tasktracker.suites;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.TeamViewTO;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.xitaymin.tasktracker.controller.TeamController.TEAMS;
import static com.xitaymin.tasktracker.dao.entity.Role.DEVELOPER;
import static com.xitaymin.tasktracker.dao.entity.Role.LEAD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamSuiteTest extends BaseTestSuite {
    private static Team mockTeam() {
        return new Team("Initial name");
    }

    private static User mockUser() {
        User user = new User();
        user.setName("John");
        user.setEmail("smith@gmail.com");
        user.getRoles().add(DEVELOPER);
        return user;
    }

    private static Project mockProject() {
        Project project = new Project();
        project.setName("Very important project");
        return project;
    }

    @DisplayName("Should return team with projects and members ids by teams id")
    @Test
    void getTeam() throws Exception {
        User savedUser = userDAO.save(mockUser());

        Team team = mockTeam();
        team.addMember(savedUser);
        Team savedTeam = teamDao.save(team);

        Project project = mockProject();
        project.addTeam(savedTeam);
        Project savedProject = projectDao.save(project);

        teamDao.flushAndClear();

        MvcResult mvcResult = mockMvc.perform(get(TEAMS + "/" + savedTeam.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TeamViewTO teamFromResponse = fromResponse(mvcResult, TeamViewTO.class);

        assertEquals(teamFromResponse.getName(), team.getName());
        assertEquals(1, teamFromResponse.getMembers().size());
        assertTrue(teamFromResponse.getMembers().contains(savedUser.getId()));
        assertEquals(1, teamFromResponse.getProjects().size());
        assertTrue(teamFromResponse.getProjects().contains(savedProject.getId()));
    }

    @DisplayName("Should return list of existing teams with projects and members ids")
    @Test
    void getAllTeams() throws Exception {

        User savedUser = userDAO.save(mockUser());

        Team team = mockTeam();
        team.addMember(savedUser);
        Team savedTeam = teamDao.save(team);

        Project project = mockProject();
        project.addTeam(savedTeam);
        Project savedProject = projectDao.save(project);

        teamDao.flushAndClear();

        MvcResult mvcResult = mockMvc.perform(get(TEAMS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TeamViewTO[] teamViewTOS = fromResponse(mvcResult, TeamViewTO[].class);

        assertEquals(1, teamViewTOS.length);

        TeamViewTO teamFromResponse = teamViewTOS[0];
        assertEquals(teamFromResponse.getName(), team.getName());
        assertEquals(1, teamFromResponse.getMembers().size());
        assertTrue(teamFromResponse.getMembers().contains(savedUser.getId()));
        assertEquals(1, teamFromResponse.getProjects().size());
        assertTrue(teamFromResponse.getProjects().contains(savedProject.getId()));
    }

    @Test
    @DisplayName("User from add team member request should be persisted in DB")
    void addTeamMember() throws Exception {
        User savedUser = userDAO.save(mockUser());

        Team savedTeam = teamDao.save(mockTeam());
        teamDao.flushAndClear();

        mockMvc.perform(put(TEAMS + "/" + savedTeam.getId() + "/member/" + savedUser.getId()))
                .andExpect(status().isOk());

        teamDao.flushAndClear();

        Team teamFromDB = teamDao.findByIdWithMembers(savedTeam.getId());
        assertEquals(1, teamFromDB.getMembers().size());
        assertTrue(teamFromDB.getMembers().contains(savedUser));
    }

    @DisplayName("After set team lead, user in DB should receive new role")
    @Test
    void setLead() throws Exception {

        User savedUser = userDAO.save(mockUser());

        Team team = new Team("Delta");
        team.addMember(savedUser);
        Team savedTeam = teamDao.save(team);

        teamDao.flushAndClear();

        mockMvc.perform(put(TEAMS + "/" + savedTeam.getId() + "/lead/" + savedUser.getId()))
                .andExpect(status().isOk());

        teamDao.flushAndClear();

        User userFromDB = userDAO.findOne(savedUser.getId());
        assertEquals(2, userFromDB.getRoles().size());
        assertTrue(userFromDB.getRoles().contains(LEAD));
    }

    @DisplayName("Team received from user should be saved to DB and retrieved by id")
    @Test
    void createTeam() throws Exception {

        String teamName = "Delta";
        CreateTeamTO teamFromRequest = new CreateTeamTO(teamName);
        MvcResult mvcResult = mockMvc.perform(post(TEAMS)
                        .accept(APPLICATION_JSON)
                        .content(asJson(teamFromRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TeamViewTO teamFromResponse = fromResponse(mvcResult, TeamViewTO.class);

        teamDao.flushAndClear();

        Team teamFromDB = teamDao.findById(teamFromResponse.getId());

        assertNotNull(teamFromDB);
        assertEquals(teamName, teamFromResponse.getName());
        assertEquals(teamName, teamFromDB.getName());
    }

    @DisplayName("Team changes received from user should be applied to DB entity")
    @Test
    void editTeam() throws Exception {
        Team team = mockTeam();
        Team savedTeam = teamDao.save(team);

        teamDao.flushAndClear();

        String newName = "New name";
        EditTeamTO editTeamRequest = new EditTeamTO(savedTeam.getId(), newName);

        mockMvc.perform(put(TEAMS)
                        .accept(APPLICATION_JSON)
                        .content(asJson(editTeamRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        teamDao.flushAndClear();

        Team updatedTeam = teamDao.findById(savedTeam.getId());
        assertEquals(newName, updatedTeam.getName());
    }

    @DisplayName("Team without members and projects should be removed from DB by id received from user")
    @Test
    void deleteTeam() throws Exception {
        Team savedTeam = teamDao.save(mockTeam());

        teamDao.flushAndClear();

        mockMvc.perform(delete(TEAMS + "/" + savedTeam.getId()))
                .andExpect(status().isOk());

        teamDao.flushAndClear();

        Team deletedTeam = teamDao.findById(savedTeam.getId());

        assertNull(deletedTeam);
    }
}
