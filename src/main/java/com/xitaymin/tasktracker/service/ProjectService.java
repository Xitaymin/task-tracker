package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    Project saveProject(CreateProjectTO project);

    void editProject(EditProjectTO project);

    void addTeam(long project, long team);

    void assignProductOwner(long projectId, long productOwnerId);

    void deleteTeam(long project, long team);
}
