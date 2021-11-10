package com.xitaymin.tasktracker.model.service;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.model.dto.project.CreateProjectTO;
import com.xitaymin.tasktracker.model.dto.project.EditProjectTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public interface ProjectService {
    Project saveProject(@Valid CreateProjectTO project);

    void editProject(@Valid EditProjectTO project);
}
