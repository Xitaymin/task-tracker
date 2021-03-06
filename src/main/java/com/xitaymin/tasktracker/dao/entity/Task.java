package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@NamedQueries({@NamedQuery(name = Task.FIND_ALL, query = "SELECT t FROM Task t"),
               @NamedQuery(name = Task.FIND_FULL_TASK_BY_ID,
                       query = "SELECT t FROM Task t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.childTasks WHERE t.id=:id"),
               @NamedQuery(name = Task.FIND_ALL_FULL_TASKS,
                       query = "SELECT DISTINCT t FROM Task t LEFT join fetch t.childTasks")})

public class Task extends BaseEntity {
    public static final String FIND_ALL = "Task.findAll";
    public static final String FIND_FULL_TASK_BY_ID = "Task.findByIdWithAll";
    public static final String FIND_ALL_FULL_TASKS = "Task.findAllWithSubtasks";

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Task parent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<Task> childTasks = new HashSet<>();

    public Task() {
    }

    public Task(String title, String description, User reporter, User assignee, Project project, TaskType type,
                Set<Task> childTasks, Task parent) {
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.project = project;
        this.type = type;
        this.childTasks = childTasks;
        this.parent = parent;
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

    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    public Set<Task> getChildTasks() {
        return childTasks;
    }

    private void setChildTasks(Set<Task> childTasks) {
        this.childTasks = childTasks;
    }

    public void addChildTask(Task task) {
        this.getChildTasks().add(task);
        task.setParent(this);
    }

    public void linkAssignee(User assignee) {
        this.setAssignee(assignee);
        assignee.getTasks().add(this);
    }
}
