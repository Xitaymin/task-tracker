package com.xitaymin.tasktracker.dao.entity;

public enum TaskType {
    EPIC(1, true), STORY(2, true), ISSUE(3, true), SUBTASK(4, false), BUG(2, false);

    private final int hierarchyLevel;
    private final boolean childable;

    TaskType(int hierarchyLevel, boolean childable) {
        this.hierarchyLevel = hierarchyLevel;
        this.childable = childable;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public boolean isChildable() {
        return childable;
    }
}
