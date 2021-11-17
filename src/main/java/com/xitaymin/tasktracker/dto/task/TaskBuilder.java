package com.xitaymin.tasktracker.dto.task;

import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dao.entity.User;

import java.util.HashSet;
import java.util.Set;

public final class TaskBuilder {
    protected long id;
    private String title;
    private String description;
    private User reporter;
    private User assignee;
    private Project project;
    private TaskType type;
    private Set<Task> childTasks = new HashSet<>();
    private Task parent;

    private TaskBuilder() {
    }

    public static TaskBuilder aTask() {
        return new TaskBuilder();
    }

    public TaskBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public TaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder withReporter(User reporter) {
        this.reporter = reporter;
        return this;
    }

    public TaskBuilder withAssignee(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public TaskBuilder withProject(Project project) {
        this.project = project;
        return this;
    }

    public TaskBuilder withType(TaskType type) {
        this.type = type;
        return this;
    }

    public TaskBuilder withChildTasks(Set<Task> childTasks) {
        this.childTasks = childTasks;
        return this;
    }

    public TaskBuilder withParent(Task parent) {
        this.parent = parent;
        return this;
    }

    public Task build() {
        return new Task(title, description, reporter, assignee, project, type, childTasks, parent);
    }
}
