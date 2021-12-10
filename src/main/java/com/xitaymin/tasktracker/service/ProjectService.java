package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;
import com.xitaymin.tasktracker.dto.project.ProjectViewTO;

import java.util.List;

public interface ProjectService {
    ProjectViewTO saveProject(CreateProjectTO project);

    void editProject(EditProjectTO project);

    void addTeam(long project, long team);

    void assignProductOwner(long projectId, long productOwnerId);

    void deleteTeam(long project, long team);

    List<ProjectViewTO> getAll();
}
