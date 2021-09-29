package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.TaskTrackerSpringContextTestSuite;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.xitaymin.tasktracker.controller.TaskController.TASKS;
import static com.xitaymin.tasktracker.controller.UserController.USERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskTestSuite extends TaskTrackerSpringContextTestSuite {

    @Test
    public void createTask() throws Exception {
        //todo fix it
        mockMvc.perform(post(USERS).content(asJson(new User(0, "User Name", "admin@gmail.com", false)))
                                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Task taskFromRequest = new Task(0, "Title", "Description", 1, null);
        mockMvc.perform(get(TASKS).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("[]"));
        MvcResult result =
                mockMvc.perform(post(TASKS).content(asJson(taskFromRequest)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        Task taskFromResponse = fromResponse(result, Task.class);
        assertEquals(taskFromResponse.getId(), 1);
        assertTrue(new ReflectionEquals(taskFromRequest, "id").matches(taskFromResponse));

        MvcResult resultAfterSave =
                mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Task[] allSavedTasks = fromResponse(resultAfterSave, Task[].class);
        assertThat(allSavedTasks.length == 1);
        Task firstSavedTask = allSavedTasks[0];

        assertThat(firstSavedTask).usingRecursiveComparison().isEqualTo(taskFromResponse);
    }

    @Test
    public void editTask() throws Exception {
        createTask();
        Task taskBeforeUpdate = new Task(1, "Updated title", "Updated description", 1, null);
        mockMvc.perform(put(TASKS).content(asJson(taskBeforeUpdate)).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)).andExpect(status().isOk());

        MvcResult resultAfterSave =
                mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Task[] allSavedTasks = fromResponse(resultAfterSave, Task[].class);
        assertThat(allSavedTasks.length == 1);
        Task updatedTask = allSavedTasks[0];

        assertThat(updatedTask).usingRecursiveComparison().isEqualTo(taskBeforeUpdate);
    }

}
