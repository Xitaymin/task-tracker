package com.xitaymin.tasktracker.suites;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.ResponseError;
import com.xitaymin.tasktracker.dto.project.ProjectBuilder;
import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.EditTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.dto.team.TeamBuilder;
import com.xitaymin.tasktracker.dto.user.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.xitaymin.tasktracker.controller.TaskController.TASKS;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskTestSuite extends BaseTestSuite {

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
    void createTask() throws Exception {
        taskListEmptyWhenNothingCreated();

        Project project =
                ProjectBuilder.aProject().withName("The most important project").withTeams(new HashSet<>()).build();
        Team team = TeamBuilder.aTeam()
                .withName("Delta")
                .withMembers(new ArrayList<>())
                .withProjects(new HashSet<>())
                .build();
        User reporter = UserBuilder.anUser()
                .withName("Reporter")
                .withEmail("reporter@gmail.com")
                .withDeleted(false)
                .withTasks(new HashSet<>())
                .build();
        userDAO.save(reporter);
        User assignee = UserBuilder.anUser()
                .withName("Assignee")
                .withEmail("assignee@gmail.com")
                .withDeleted(false)
                .withTasks(new HashSet<>())
                .build();
        assignee.setTeam(team);
        team.getMembers().add(assignee);
        project.getTeams().add(team);
        team.getProjects().add(project);

        projectDao.save(project);

        CreateTaskTO taskFromRequest = new CreateTaskTO("Serious task",
                "Task for true mans",
                reporter.getId(),
                assignee.getId(),
                project.getId(),
                TaskType.EPIC,
                null);

        MvcResult result = mockMvc.perform(post(TASKS).content(asJson(taskFromRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        TaskViewTO taskFromResponse = fromResponse(result, TaskViewTO.class);

        Assertions.assertNotEquals(taskFromResponse.getId(), 0);

        MvcResult resultAfterSave =
                mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();

        TaskViewTO[] allSavedTasks = fromResponse(resultAfterSave, TaskViewTO[].class);

        assertThat(allSavedTasks.length == 1);

        TaskViewTO taskAfterSave = allSavedTasks[0];

        Assertions.assertEquals(taskAfterSave, taskFromResponse);

    }

    @Test
    void editTask() throws Exception {
        createTask();
        Collection<Task> tasks = taskDao.findAll();
        Task savedTask = tasks.iterator().next();

        EditTaskTO taskFromRequest = new EditTaskTO(savedTask.getId(), "Updated title", "Updated description");
        mockMvc.perform(put(TASKS).content(asJson(taskFromRequest))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)).andExpect(status().isOk());
        MvcResult resultAfterSave =
                mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        TaskViewTO[] allSavedTasks = fromResponse(resultAfterSave, TaskViewTO[].class);
        assertThat(allSavedTasks.length == 1);
        TaskViewTO taskAfterUpdate = allSavedTasks[0];

        Assertions.assertEquals(taskFromRequest.getTitle(), taskAfterUpdate.getTitle());
        Assertions.assertEquals(taskFromRequest.getDescription(), taskAfterUpdate.getDescription());
    }

}
