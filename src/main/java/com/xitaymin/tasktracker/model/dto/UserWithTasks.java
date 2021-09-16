package com.xitaymin.tasktracker.model.dto;
import com.xitaymin.tasktracker.dao.entity.User;

import java.util.List;

public class UserWithTasks {
    private User user;
    private List<Long> tasksId;

    public UserWithTasks(User user, List<Long> tasksId) {
        this.user = user;
        this.tasksId = tasksId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Long> getTasksId() {
        return tasksId;
    }

    public void setTasksId(List<Long> tasksId) {
        this.tasksId = tasksId;
    }
}

