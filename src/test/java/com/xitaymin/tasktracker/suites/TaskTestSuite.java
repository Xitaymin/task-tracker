package com.xitaymin.tasktracker.suites;

import com.xitaymin.tasktracker.dto.ResponseError;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.xitaymin.tasktracker.controller.TaskController.TASKS;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskTestSuite extends BaseTestSuite {

//    @BeforeEach
//    public void setUp() throws Exception {
//        mockMvc.perform(post(USERS).content(asJson(new User(0, "User Name", "admin@gmail.com", false))).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//    }

    @Test
    void taskListEmptyWhenNothingCreated() throws Exception {
        mockMvc.perform(get(TASKS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void notFoundWhenRequestNotExistingTask() throws Exception {
        taskListEmptyWhenNothingCreated();
        String absentId = "1";
        MvcResult mvcResult =
                mockMvc.perform(get(TASKS + "/{id}", absentId)).andExpect(status().isNotFound()).andReturn();

        ResponseError responseError = fromResponse(mvcResult, ResponseError.class);
        assertThat(responseError.getMessage()).isEqualTo(String.format(TASK_NOT_FOUND, absentId));
    }


    @Test
    public void createTask() throws Exception {
//        User reporter = new User();

//        Task taskFromRequest = new Task(0, "Title", "Description", 1, null);
//        mockMvc.perform(get(TASKS).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("[]"));
//        MvcResult result = mockMvc.perform(post(TASKS).content(asJson(taskFromRequest))
//                                                   .contentType(MediaType.APPLICATION_JSON)
//                                                   .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//
//        Task taskFromResponse = fromResponse(result, Task.class);
//        assertEquals(taskFromResponse.getId(), 1);
//        assertTrue(new ReflectionEquals(taskFromRequest, "id").matches(taskFromResponse));
//
//        MvcResult resultAfterSave = mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        Task[] allSavedTasks = fromResponse(resultAfterSave, Task[].class);
//        assertThat(allSavedTasks.length == 1);
//        Task taskAfterSave = allSavedTasks[0];
//
//        assertThat(taskAfterSave).usingRecursiveComparison().isEqualTo(taskFromResponse);
//    }
//
//    @Test
//    public void editTask() throws Exception {
//        createTask();
//        Task taskFromRequest = new Task(1, "Updated title", "Updated description", 1, null);
//        mockMvc.perform(put(TASKS).content(asJson(taskFromRequest)).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)).andExpect(status().isOk());
//        MvcResult resultAfterSave = mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        Task[] allSavedTasks = fromResponse(resultAfterSave, Task[].class);
//        assertThat(allSavedTasks.length == 1);
//        Task taskAfterUpdate = allSavedTasks[0];
//
//        assertThat(taskAfterUpdate).usingRecursiveComparison().isEqualTo(taskFromRequest);
    }

}
