package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
@NamedQueries({@NamedQuery(name = Task.FIND_ALL, query = "SELECT t FROM Task t"),
               @NamedQuery(name = Task.FIND_BY_ASSIGNEE, query = "SELECT t FROM Task t WHERE t.assignee=:assignee")})

public class Task extends PersistentObject {
    public static final String FIND_ALL = "Task.findAll";
    public static final String FIND_BY_ASSIGNEE = "Task.findByAssignee";
    private String title;
    private String description;
    @ManyToOne(optional = false)
    @JoinColumn(name = "reporter_id")
    private User reporter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    private TaskType type;

//    private List<Task> childTasks;

    public Task() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

}
