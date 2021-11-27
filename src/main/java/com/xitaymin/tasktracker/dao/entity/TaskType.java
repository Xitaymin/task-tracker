package com.xitaymin.tasktracker.dao.entity;

public enum TaskType {
    EPIC(null), STORY(EPIC), ISSUE(STORY), SUBTASK(ISSUE), BUG(EPIC);

    private final TaskType parent;

    TaskType(TaskType parent) {
        this.parent = parent;
    }

    public TaskType getParent() {
        return parent;
    }
}
