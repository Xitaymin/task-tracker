package com.xitaymin.tasktracker.service;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.dto.project.EditProjectTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public interface ProjectService {
    Project saveProject(@Valid CreateProjectTO project);

    void editProject(@Valid EditProjectTO project);

    void addTeam(long project, long team);

    void assignProductOwner(long projectId, long productOwnerId);
}
