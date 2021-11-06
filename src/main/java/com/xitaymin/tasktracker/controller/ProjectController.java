package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.model.dto.CreateProjectTO;
import com.xitaymin.tasktracker.model.service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

//    @PutMapping
//    public void editProject(@Valid @RequestBody EditProjectTO project){
//        projectService.editProject(project);
//    }
}