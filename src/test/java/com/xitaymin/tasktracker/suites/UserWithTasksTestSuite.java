package com.xitaymin.tasktracker.suites;


import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.project.ProjectBuilder;
import com.xitaymin.tasktracker.dto.task.TaskBuilder;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.dto.team.TeamBuilder;
import com.xitaymin.tasktracker.dto.user.UserBuilder;
import com.xitaymin.tasktracker.dto.user.UserWithTasksAndTeamsTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static com.xitaymin.tasktracker.controller.TaskController.TASKS;
import static com.xitaymin.tasktracker.controller.UserController.USERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWithTasksTestSuite extends BaseTestSuite {

    @Test
    void assignTask() throws Exception {
        Project project = ProjectBuilder.aProject().withName("The most important project").build();
        Team team = TeamBuilder.aTeam()
                .withName("Delta")
                .build();
        User reporter = UserBuilder.anUser()
                .withName("Reporter")
                .withEmail("reporter@gmail.com")
                .withDeleted(false)
                .build();
        userDAO.save(reporter);
        User assignee = UserBuilder.anUser()
                .withName("Assignee")
                .withEmail("assignee@gmail.com")
                .withDeleted(false)
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

        taskDao.save(task);

        mockMvc.perform(put(USERS + "/" + assignee.getId() + "/task/" + task.getId())).andExpect(status().isOk());

        MvcResult resultAfterSave = mockMvc.perform(get(TASKS).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TaskViewTO[] allSavedTasks = fromResponse(resultAfterSave, TaskViewTO[].class);
        assertEquals(allSavedTasks.length, 1);
        TaskViewTO firstSavedTask = allSavedTasks[0];
        assertEquals(firstSavedTask.getAssignee(), assignee.getId());
    }

    private User prepareUserWithTasks() {
        Project project = ProjectBuilder.aProject().withName("The most important project").build();
        Team team = TeamBuilder.aTeam().withName("Delta").build();
        User reporter = UserBuilder.anUser()
                .withName("Reporter")
                .withEmail("reporter@gmail.com")
                .withDeleted(false)
                .withTasks(new HashSet<>())
                .build();
        userDAO.save(reporter);
        Set<Role> assigneeRoles = new HashSet<>();
        assigneeRoles.add(Role.DEVELOPER);
        assigneeRoles.add(Role.LEAD);
        User assignee = UserBuilder.anUser()
                .withName("Assignee")
                .withEmail("assignee@gmail.com")
                .withDeleted(false)
                .withTasks(new HashSet<>())
                .withRoles(assigneeRoles)
                .build();
        assignee.setTeam(team);
        team.getMembers().add(assignee);
        project.getTeams().add(team);
        team.getProjects().add(project);

        projectDao.save(project);

        Task task = TaskBuilder.aTask()
                .withTitle("Title")
                .withDescription("Description")
                .withAssignee(assignee)
                .withReporter(reporter)
                .withType(TaskType.EPIC)
                .withProject(project)
                .build();

        Task firstTask = taskDao.save(task);
        assignee.getTasks().add(firstTask);

        Task secondTask = TaskBuilder.aTask()
                .withTitle("Second task")
                .withDescription("Description")
                .withAssignee(assignee)
                .withReporter(reporter)
                .withType(TaskType.BUG)
                .withParent(firstTask)
                .withProject(project)
                .build();

        secondTask.getParent().getChildTasks().add(secondTask);
        assignee.getTasks().add(secondTask);

        taskDao.save(secondTask);
        return assignee;
    }

    @Test
    void getUserWithTasks() throws Exception {
        User savedUser = prepareUserWithTasks();

        MvcResult result = mockMvc.perform((get(USERS + "/" + savedUser.getId()).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))).andExpect(status().isOk()).andReturn();
        UserWithTasksAndTeamsTO userWithTasks = fromResponse(result, UserWithTasksAndTeamsTO.class);
        Set<Long> tasks = userWithTasks.getTasksId();
        assertEquals(tasks.size(), savedUser.getTasks().size());
        assertEquals(savedUser.getTeam().getId(), userWithTasks.getTeamId());
    }
}
