package com.xitaymin.tasktracker.model.dto;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;

import java.util.List;

public class UserTasks {
    private User user;
    private List<Task> tasks;

    public UserTasks(User user, List<Task> tasks) {
        this.user = user;
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

