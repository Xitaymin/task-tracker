package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;
import com.xitaymin.tasktracker.service.ProjectService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

//Создание проекта. +
//Редактировать можно только имя +
//Удалять проект нельзя. +
//Добавить/удалить команду в проект. +
//Назначить productOwner, он должен иметь роль MANAGER +

@RestController
@RequestMapping(ProjectController.PROJECTS)
public class ProjectController {

    public static final String PROJECTS = "/projects";
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping()
    public Project createProject(@Valid @RequestBody CreateProjectTO project) {
        return projectService.saveProject(project);
    }

    @PutMapping
    public void editProject(@Valid @RequestBody EditProjectTO project) {
        projectService.editProject(project);
    }

    @PutMapping("/{project}/team/{team}")
    public void addTeam(@PathVariable @Positive long project, @PathVariable @Positive long team) {
        projectService.addTeam(project, team);
    }

    @DeleteMapping("/{project}/team/{team}")
    public void deleteTeam(@PathVariable @Positive long project, @PathVariable @Positive long team) {
        projectService.deleteTeam(project, team);
    }

    @PutMapping("/{projectId}/owner/{productOwnerId}")
    public void assignProductOwner(@PathVariable @Positive long projectId,
                                   @PathVariable @Positive long productOwnerId) {
        projectService.assignProductOwner(projectId, productOwnerId);
    }
}