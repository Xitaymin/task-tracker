package com.xitaymin.tasktracker.suites;


import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.project.ProjectBuilder;
import com.xitaymin.tasktracker.dto.task.TaskBuilder;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.dto.team.TeamBuilder;
import com.xitaymin.tasktracker.dto.user.UserBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;

import static com.xitaymin.tasktracker.controller.TaskController.TASKS;
import static com.xitaymin.tasktracker.controller.UserController.USERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWithTasksTestSuite extends BaseTestSuite {


//    @BeforeEach
//    public void setUp() throws Exception {
//        mockMvc.perform(post(USERS).content(asJson(new User(0, "First user", "first@gmail.com", false))).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        mockMvc.perform(post(TASKS).content(asJson(new Task(0, "First task", "Description", 1, null)))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//    }

    @Test
    void assignTask() throws Exception {
        Project project = ProjectBuilder.aProject().withName("The most important project").build();
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

        Task task = TaskBuilder.aTask()
                .withTitle("Title")
                .withDescription("Description")
                .withAssignee(null)
                .withReporter(reporter)
                .withType(TaskType.BUG)
                .withProject(project)
                .build();

        Task savedTask = taskDao.save(task);

        mockMvc.perform(put(USERS + "/" + assignee.getId() + "/task/" + task.getId())).andExpect(status().isOk());

        MvcResult resultAfterSave = mockMvc.perform(get(TASKS).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TaskViewTO[] allSavedTasks = fromResponse(resultAfterSave, TaskViewTO[].class);
        assertEquals(allSavedTasks.length, 1);
        TaskViewTO firstSavedTask = allSavedTasks[0];
        assertEquals(firstSavedTask.getAssignee(), assignee.getId());
    }

    private Task getSavedTask() {
        Project project = ProjectBuilder.aProject().withName("The most important project").build();
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

        Task task = TaskBuilder.aTask()
                .withTitle("Title")
                .withDescription("Description")
                .withAssignee(null)
                .withReporter(reporter)
                .withType(TaskType.BUG)
                .withProject(project)
                .build();

        return taskDao.save(task);
    }

    @Test
    void getUserWithTasks() throws Exception {
//        assignTask();
//        Task task = taskDao.findAll().iterator().next();
//        User user = task.getAssignee();
//
//        MvcResult result = mockMvc.perform((get(USERS + user.getId()).contentType(APPLICATION_JSON).accept(APPLICATION_JSON)))
//                .andExpect(status().isOk())
//                .andReturn();
//        UserWithTasksAndTeamsTO userWithTasks = fromResponse(result, UserWithTasksAndTeamsTO.class);
//        Set<TaskViewTO> tasks = userWithTasks.getTasksId();
//        assertThat(tasks).hasSize(1);
//        assertEquals(tasks.get(0).getId(), 1);
    }
}
