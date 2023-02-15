package com.xitaymin.tasktracker.suites;

import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.TeamViewTO;
import com.xitaymin.tasktracker.dto.team.CreateTeamTO;
import com.xitaymin.tasktracker.dto.team.EditTeamTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.xitaymin.tasktracker.controller.TeamController.TEAMS;
import static com.xitaymin.tasktracker.controller.UserController.USERS;
import static com.xitaymin.tasktracker.dao.entity.Role.DEVELOPER;
import static com.xitaymin.tasktracker.dao.entity.Role.LEAD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamSuiteTest extends BaseTestSuite {

    @Test
    void teamListEmptyWhenNothingCreated() throws Exception {
        mockMvc.perform(get(TEAMS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void getTeam() throws Exception {
        User user = new User();
        user.setName("John");
        user.setEmail("smith@gmail.com");
        user.getRoles().add(DEVELOPER);

        User savedUser = userDAO.saveAndFlush(user);
        userDAO.clear();

        Team team = new Team();
        team.setName("Delta");
        Team savedTeam = teamDao.save(team);
        teamDao.flushAndClear();

        mockMvc.perform(put(TEAMS + "/" + savedTeam.getId() + "/member/" + savedUser.getId()))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get(TEAMS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        TeamViewTO[] teamViewTOS = fromResponse(mvcResult, TeamViewTO[].class);
        assertEquals(teamViewTOS.length, 1);
        assertEquals(teamViewTOS[0].getName(), team.getName());
        assertEquals(teamViewTOS[0].getMembers().size(), 1);
        assertTrue(teamViewTOS[0].getMembers().contains(savedUser.getId()));

        mockMvc.perform(put(TEAMS + "/" + savedTeam.getId() + "/lead/" + savedUser.getId()))
                .andExpect(status().isOk());

        MvcResult mvcUserResult = mockMvc.perform(get(USERS))
                .andExpect(status().isOk())
                .andReturn();

        UserViewTO[] userViewTOS = fromResponse(mvcUserResult, UserViewTO[].class);
        assertEquals(userViewTOS.length, 1);

        UserViewTO userFromResponse = userViewTOS[0];

        assertEquals(userFromResponse.getId(), savedUser.getId());
        assertEquals(userFromResponse.getRoles().size(), 2);
        assertTrue(userFromResponse.getRoles().contains(LEAD));
        assertTrue(userFromResponse.getRoles().contains(DEVELOPER));
    }

    @DisplayName("Team received from user should be saved to DB and retrieved by id")
    @Test
    void createTeam() throws Exception {
        CreateTeamTO teamFromRequest = new CreateTeamTO("Delta");
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
        assertEquals(teamFromDB.getName(), teamFromResponse.getName());
    }

    @DisplayName("Team changes received from user should be applied to DB entity")
    @Test
    void editTeam() throws Exception {
        Team team = new Team();
        team.setName("Initial name");
        Team savedTeam = teamDao.save(team);

        teamDao.flushAndClear();

        EditTeamTO editTeamRequest = new EditTeamTO(savedTeam.getId(), "New name");

        mockMvc.perform(put(TEAMS)
                        .accept(APPLICATION_JSON)
                        .content(asJson(editTeamRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        teamDao.flushAndClear();

        Team updatedTeam = teamDao.findById(savedTeam.getId());
        assertEquals(updatedTeam.getName(), "New name");
    }
}
