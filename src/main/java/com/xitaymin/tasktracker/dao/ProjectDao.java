package com.xitaymin.tasktracker.dao;

import com.xitaymin.tasktracker.dao.entity.Project;

public interface ProjectDao {

    Project save(Project project);

    void update(Project project);
}
