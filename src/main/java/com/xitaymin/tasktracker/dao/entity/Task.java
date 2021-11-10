package com.xitaymin.tasktracker.dao.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tasks")
@NamedQueries({@NamedQuery(name = Task.FIND_ALL, query = "SELECT t FROM Task t"),
               @NamedQuery(name = Task.FIND_BY_ASSIGNEE, query = "SELECT t FROM Task t WHERE t.assignee=:assignee")})

public class Task extends PersistentObject {
    public static final String FIND_ALL = "Task.findAll";
    public static final String FIND_BY_ASSIGNEE = "Task.findByAssignee";
    private String title;
    private String description;
    private long reporter;
    private Long assignee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    private TaskType type;
//    private List<Task> childTasks;

    public Task() {
    }

    public Task(long id, @NotBlank String title, @NotBlank String description, long reporter, Long assignee,
                Project project) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.project = project;
    }

//    public Task(long id, @NotBlank String title, @NotBlank String description, long reporter, Long assignee,
//                Project project, TaskType type, List<Task> childTasks) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.reporter = reporter;
//        this.assignee = assignee;
//        this.project = project;
//        this.type = type;
//        this.childTasks = childTasks;
//    }


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

    public long getReporter() {
        return reporter;
    }

    public void setReporter(long reporter) {
        this.reporter = reporter;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
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
//
//    public List<Task> getChildTasks() {
//        return childTasks;
//    }
//
//    public void setChildTasks(List<Task> childTasks) {
//        this.childTasks = childTasks;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Task task = (Task) o;
//        return reporter == task.reporter && title.equals(task.title) && description.equals(task.description) && Objects.equals(
//                assignee,
//                task.assignee) && Objects.equals(project, task.project) && type == task.type && Objects.equals(
//                childTasks,
//                task.childTasks);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(title, description, reporter, assignee, project, type, childTasks);
//    }
}
