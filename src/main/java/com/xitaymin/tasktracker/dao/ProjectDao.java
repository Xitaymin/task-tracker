package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Project;

public interface ProjectDao {

    Project save(Project project);

    Project findById(long id);

    Project findByIdWithTeams(long projectId);
}
