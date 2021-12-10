package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;
import com.xitaymin.tasktracker.dto.project.ProjectViewTO;
import com.xitaymin.tasktracker.service.ProjectService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(ProjectController.PROJECTS)
public class ProjectController {

    public static final String PROJECTS = "/projects";
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping()
    public ProjectViewTO createProject(@Valid @RequestBody CreateProjectTO project) {
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

    @GetMapping
    public List<ProjectViewTO> getAllProjects() {
        return projectService.getAll();
    }
}