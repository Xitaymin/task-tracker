package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.TaskTrackerSpringContextTestSuite;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.UserWithTasks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.xitaymin.tasktracker.controller.TaskController.TASKS;
import static com.xitaymin.tasktracker.controller.UserController.USERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWithTasksTestSuite extends TaskTrackerSpringContextTestSuite {
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc.perform(post(USERS).content(asJson(new User(0, "User Name", "admin@gmail.com", false)))
                                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Task taskFromRequest = new Task(0, "Title", "Description", 1, null);

        MvcResult result =
                mockMvc.perform(post(TASKS).content(asJson(taskFromRequest)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

    }

    @Test
    public void assignTask() throws Exception {
        //todo create user here
        //todo create task here
        mockMvc.perform(put(USERS + "/" + 1 + "/task/" + 1)).andExpect(status().isOk());
        MvcResult resultAfterSave =
                mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Task[] allSavedTasks = fromResponse(resultAfterSave, Task[].class);
        assertThat(allSavedTasks.length == 1);
        Task firstSavedTask = allSavedTasks[0];
        assertEquals(firstSavedTask.getAssignee(), 1);

    }

    @Test
    public void getUserWithTasks() throws Exception {
        assignTask();
        MvcResult result =
                mockMvc.perform((get(USERS + "/" + 1).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))).andExpect(status().isOk()).andReturn();
        UserWithTasks userWithTasks = fromResponse(result, UserWithTasks.class);
        List<Task> tasks = userWithTasks.getTasks();
        assertThat(tasks).hasSize(1);
        assertEquals(tasks.get(0).getId(), 1);
    }
}
